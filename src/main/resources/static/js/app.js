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
        url: 'http://localhost/aye-aye/',
        addModal: false,
        editModal: false,
        deleteModal: false,
        produits: [],
        categories: [],
        cars: [],
        guides: [],
        search: { text: '' },
        emptyResult: false,
        newProduct: {
            experience_id: '',
            name: '',
            category_id: '',
            category: '',
            description: '',
            car_id: '',
            driver: '',
            max_traveler: '',
            first_guide: '',
            guide1: '',
            second_guide: '',
            guide2: '',
            picture: ''
        },
        chooseProduct: {},
        formValidate: [],
        successMSG: '',

        FILE: null,

        //pagination
        currentPage: 0,
        rowCountPage: 5,
        totalProduits: 0,
        pageRange: 2
    },
    created() {
        this.showAll();
        this.getCategory();
        this.getCars();
        this.getGuides();
    },
    methods: {
        onFileChange(e) {
            var files = e.target.files;
            if (!files.length)
                return;
            v.FILE = files[0];
            v.createImage(files[0]);
        },
        createImage(file) {
            v.newProduct.picture = new Image();
            var reader = new FileReader();
            reader.onload = (e) => {
                v.newProduct.picture = e.target.result;
                v.chooseProduct.picture = e.target.result;
            };
            reader.readAsDataURL(file);
        },
        getCars() {
            axios.get(this.url + "car/showall").then(function(response) {
                if (response.data.cars == null) {
                    v.noResult()
                } else {
                    v.cars = response.data.cars;
                }
            })
        },
        getGuides() {
            axios.get(this.url + "guide/showall").then(function(response) {
                if (response.data.guides == null) {
                    v.noResult()
                } else {
                    v.guides = response.data.guides;
                }
            })
        },
        getCategory() {
            axios.get(this.url + "experience_category/showall").then(function(response) {
                if (response.data.categories == null) {
                    v.noResult()
                } else {
                    v.categories = response.data.categories;
                }
            })
        },
        showAll() {
            axios.get(this.url + "experience/showAll").then(function(response) {
                if (response.data.produits == null) {
                    v.noResult()
                } else {
                    v.getData(response.data.produits);
                }
            })
        },
        searchProduct() {
            var formData = v.formData(v.search);
            axios.post(this.url + "experience/searchProduct", formData).then(function(response) {
                if (response.data.produits == null) {
                    v.noResult()
                } else {
                    v.getData(response.data.produits);
                }
            })
        },
        addProduct() {
            var formData = v.formData(v.newProduct);
            formData.append('file', v.FILE);
            axios.post(this.url + "experience/addProduct", formData).then(function(response) {
                if (response.data.error) {
                    v.formValidate = response.data.msg;
                } else {
                    v.successMSG = response.data.msg;
                    v.clearAll();
                    v.clearMSG();
                }
            })
        },
        updateProduct() {
            var formData = v.formData(v.chooseProduct);
            formData.append('file', v.FILE);
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
            var formData = v.formData(v.chooseProduct);
            axios.post(this.url + "experience/deleteProduct", formData).then(function(response) {
                if (!response.data.error) {
                    v.successMSG = response.data.success;
                    v.clearAll();
                    v.clearMSG();
                }
            })
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
        },
        clearMSG() {
            setTimeout(function() {
                v.successMSG = ''
            }, 3000);
        },
        clearAll() {
            v.newProduct = {
                experience_id: '',
                name: '',
                category_id: '',
                category: '',
                description: '',
                car_id: '',
                driver: '',
                max_traveler: '',
                first_guide: '',
                guide1: '',
                second_guide: '',
                guide2: '',
                picture: ''
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
            v.search.text ? v.searchProduct() : v.showAll();
        }
    },
    filters: {
        limit100: function(s) {
            return s.substring(0, 100);
        }
    }
})