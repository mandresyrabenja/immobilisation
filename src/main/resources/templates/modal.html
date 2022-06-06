<!--ajout produit-->
<modal v-if="addModal" @close="clearAll()">

<h3 slot="head" >Ajouter un expérience</h3>
<div slot="body" class="row">
    <div class="col-md-12">
        <div class="row">
            <div class="col-md-6">
                <div class="form-group">
                    <label>Nom</label>
                    <input type="text" class="form-control" :class="{'is-valid': formValidate.name}" name="name" v-model="newProduct.name">                
                    <div class="has-text-danger" v-html="formValidate.name"> </div>
                </div>    
            </div>
            <div class="col-md-6">
                <div class="form-group">
                    <label>Catégories</label>
                    <select name="category_id" id="category_id" class="form-control" :class="{'is-valid': formValidate.category_id}" v-model="newProduct.category_id">
                        <option v-for="cat in categories" :value="cat.category_id">
                            {{cat.name}}
                        </option>
                    </select>                
                    <div class="has-text-danger" v-html="formValidate.category_id"> </div>
                </div>            
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
                <div class="form-group">
                    <label>Chauffeur</label>
                    <select name="car_id" class="form-control" :class="{'is-valid': formValidate.car_id}" v-model="newProduct.car_id">
                        <option v-for="car in cars" :value="car.car_id">
                            {{car.driver}}
                        </option>
                    </select>                
                    <div class="has-text-danger" v-html="formValidate.car_id"> </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="form-group">
                    <label>Voyageurs max</label>
                    <input type="number" class="form-control" :class="{'is-valid': formValidate.max_traveler}" name="max_traveler" v-model="newProduct.max_traveler">                
                    <div class="has-text-danger" v-html="formValidate.max_traveler"> </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
                <div class="form-group">
                    <label>Premier guide</label>
                    <select name="first_guide" class="form-control" :class="{'is-valid': formValidate.first_guide}" v-model="newProduct.first_guide">
                        <option v-for="guide in guides" :value="guide.guide_id">
                            {{guide.name}}
                        </option>
                    </select>                
                    <div class="has-text-danger" v-html="formValidate.first_guide"> </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="form-group">
                    <label>Second guide</label>
                    <select name="second_guide_id" class="form-control" :class="{'is-valid': formValidate.second_guide_id}" v-model="newProduct.second_guide_id">
                        <option v-for="guide in guides" :value="guide.guide_id">
                            {{guide.name}}
                        </option>
                    </select>                
                    <div class="has-text-danger" v-html="formValidate.second_guide_id"> </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="form-group">
                    <label>Description</label>
                    <textarea cols="25" rows="4" type="text" class="form-control" :class="{'is-valid': formValidate.description}" name="description" v-model="newProduct.description">
                    </textarea>
                    <div class="has-text-danger" v-html="formValidate.description"> </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="form-group">
                    <label>Image</label>
                    <small id="picHelp" class="text-muted">Image de l'expérience</small>
                    <input type="file" class="form-control" :class="{'is-valid': formValidate.picture}" name="picture" accept="image/png, image/jpeg, image/jpg" aria-describedby="picHelp" @change="onFileChange">
                    <div class="has-text-danger" v-html="formValidate.picture"> </div>
                </div>
            </div>
        </div>
    </div>
    <div slot="foot">
        <button class="btn btn-success" @click="addProduct">Ajouter</button>
    </div>
</div>
</modal>



<!--modifier-->

<modal v-if="editModal" @close="clearAll()">
<h3 slot="head" >Modifier l'éxperience</h3>
<div slot="body" class="row">
<div class="col-md-12">
        <div class="row">
            <div class="col-md-6">
                <div class="form-group">
                    <label>Nom</label>
                    <input type="text" class="form-control" :class="{'is-valid': formValidate.name}" name="name" v-model="chooseProduct.name">                
                    <div class="has-text-danger" v-html="formValidate.name"> </div>
                </div>    
            </div>
            <div class="col-md-6">
                <div class="form-group">
                    <label>Catégories</label>
                    <select name="category_id" id="category_id" class="form-control" :class="{'is-valid': formValidate.category_id}" v-model="chooseProduct.category_id">
                        <option v-for="cat in categories" :value="cat.category_id">
                            {{cat.name}}
                        </option>
                    </select>                
                    <div class="has-text-danger" v-html="formValidate.category_id"> </div>
                </div>            
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
                <div class="form-group">
                    <label>Chauffeur</label>
                    <select name="car_id" class="form-control" :class="{'is-valid': formValidate.car_id}" v-model="chooseProduct.car_id">
                        <option v-for="car in cars" :value="car.car_id">
                            {{car.driver}}
                        </option>
                    </select>                
                    <div class="has-text-danger" v-html="formValidate.car_id"> </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="form-group">
                    <label>Voyageurs max</label>
                    <input type="number" class="form-control" :class="{'is-valid': formValidate.max_traveler}" name="max_traveler" v-model="chooseProduct.max_traveler">                
                    <div class="has-text-danger" v-html="formValidate.max_traveler"> </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6">
                <div class="form-group">
                    <label>Premier guide</label>
                    <select name="first_guide" class="form-control" :class="{'is-valid': formValidate.first_guide}" v-model="chooseProduct.first_guide">
                        <option v-for="guide in guides" :value="guide.guide_id">
                            {{guide.name}}
                        </option>
                    </select>                
                    <div class="has-text-danger" v-html="formValidate.first_guide"> </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="form-group">
                    <label>Second guide</label>
                    <select name="second_guide_id" class="form-control" :class="{'is-valid': formValidate.second_guide_id}" v-model="chooseProduct.second_guide_id">
                        <option v-for="guide in guides" :value="guide.guide_id">
                            {{guide.name}}
                        </option>
                    </select>                
                    <div class="has-text-danger" v-html="formValidate.second_guide_id"> </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="form-group">
                    <label>Description</label>
                    <textarea cols="25" rows="4" type="text" class="form-control" :class="{'is-valid': formValidate.description}" name="description" v-model="chooseProduct.description">
                    </textarea>                
                    <div class="has-text-danger" v-html="formValidate.description"> </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="form-group">
                    <label>Image</label>
                    <small id="picHelp" class="text-muted">Image de l'expérience</small>
                    <input type="file" class="form-control" :class="{'is-valid': formValidate.picture}" name="picture" accept="image/png, image/jpeg, image/jpg" aria-describedby="picHelp" @change="onFileChange">
                    <div class="has-text-danger" v-html="formValidate.picture"> </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div slot="foot">
    <button class="btn btn-success" @click="updateProduct">Modifier</button>
</div>
</modal>


<!--effacer-->
<modal v-if="deleteModal" @close="clearAll()">
    <h3 slot="head">Effacer</h3>
    <div slot="body" class="text-center">Voulez-vous effacer cet produit?</div>
    <div slot="foot">
        <div class="row">
            <div class="col-md-6">
                <button class="btn btn-danger" @click="deleteModal = false; deleteProduct()" >Effacer</button>                    
            </div>
            <div class="col-md-6">
                <button class="btn" @click="deleteModal = false">Annuler</button>                        
            </div>
        </div>
    </div>
</modal>