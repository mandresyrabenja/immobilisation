Vue.component('modal', { //modal
    template: `
      <transition
                enter-active-class="animated rollIn"
                     leave-active-class="animated rollOut">
    <div class="modal is-active" >
  <div class="modal-card border border border-secondary">
    <div class="modal-card-head text-center">
    <div class="modal-card-title text-white">
          <slot name="head"></slot>
    </div>
<button class="delete" @click="$emit('close')"></button>
    </div>
    <div class="modal-card-body">
         <slot name="body"></slot>
    </div>
    <div class="modal-card-foot" >
      <slot name="foot"></slot>
    </div>
  </div>
</div>
</transition>
    `
})
var v = new Vue({
    el: '#app',
    data: {
        url: 'http://localhost:8080/api/v1/assets',
        addModal: false,
        editModal: false,
        deleteModal: false,
        produits: [],
        categories: [],
        cars: [],
        guides: [],
        search: { keyword: '' },
        emptyResult: false,
        newProduct: {
            "name": "",
            "purchasePrice": 0,
            "usage": 0,
            "deprecationType": "",
            "purchaseDate": "",
            "commissioningDate": ""
        },
        chooseProduct: {},
        formValidate: [],
        successMSG: '',
        deprecations: [],

        //pagination
        currentPage: 0,
        rowCountPage: 5,
        totalProduits: 0,
        pageRange: 2
    },
    created() {
        this.showAll();
    },
    methods: {
        showAll() {
            axios.get(this.url).then(function(response) {
                if (response.data == null) {
                    v.noResult()
                } else {
                    v.getData(response.data);
                }
            })
        },
        searchProduct() {
            let formData = v.formData(v.search);
            axios.get(this.url + "/search?keyword=" + v.search.keyword).then(
                function(response) {
                    if (response.data == null) {
                        v.noResult()
                    } else {
                        v.getData(response.data);
                    }
                }
            ).catch(
                function(error) {
                    console.log(error);
                }
            );
        },
        addProduct() {

            axios.post(this.url, v.newProduct)
                .then(function(response) {
                    v.successMSG = response.data;
                    v.clearAll();
                    v.clearMSG();
                }).catch(function (error) {
                    console.log(error);
                });
        },
        updateProduct() {
            var formData = v.formData(v.chooseProduct);
            axios.post(this.url + "experience/updateProduct", formData).then(function(response) {
                if (response.data.error) {
                    v.formValidate = response.data.msg;
                } else {
                    v.successMSG = response.data.success;
                    v.clearAll();
                    v.clearMSG();
                }
            })
        },
        deleteProduct() {
            axios.delete(this.url + "/" + v.chooseProduct.id).then(function(response) {
                    v.successMSG = response.data;
                    v.clearAll();
                    v.clearMSG();
            }).catch(
                function (error) {
                    console.log(error);
                }
            );
        },
        formData(obj) {
            var formData = new FormData();
            for (var key in obj) {
                formData.append(key, obj[key]);
            }
            return formData;
        },
        getData(produits) {
            v.emptyResult = false;
            v.totalProduits = produits.length;
            v.produits = produits.slice(v.currentPage * v.rowCountPage, (v.currentPage * v.rowCountPage) + v.rowCountPage);

            // Raha vide dia miverina page 1
            if (v.produits.length == 0 && v.currentPage > 0) {
                v.pageUpdate(v.currentPage - 1)
                v.clearAll();
            }
        },

        selectProduct(produit) {
            v.chooseProduct = produit;
            v.findDeprecation(produit.id);
        },
        findDeprecation(id) {
            axios.get(this.url + '/' + id + '/deprecation')
                .then(function(response) {
                    v.deprecations = response.data;
                    console.log(response.data);
                }).catch(function (error) {
                console.log(error);
            });
        },
        clearMSG() {
            setTimeout(function() {
                v.successMSG = ''
            }, 3000);
        },
        clearAll() {
            v.newProduct = {
                "name": "",
                "purchasePrice": 0,
                "usage": 0,
                "deprecationType": "",
                "purchaseDate": "",
                "commissioningDate": ""
            };
            v.formValidate = false;
            v.addModal = false;
            v.editModal = false;
            v.deleteModal = false;
            v.refresh()

        },
        noResult() {

            v.emptyResult = true;
            v.produits = null
            v.totalProduits = 0

        },
        pageUpdate(pageNumber) {
            v.currentPage = pageNumber;
            v.refresh();
        },
        refresh() {
            v.search.keyword ? v.searchProduct() : v.showAll();
        }
    },
    filters: {
        limit100: function(s) {
            return s.substring(0, 100);
        }
    }
})