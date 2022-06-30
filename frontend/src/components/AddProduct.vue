<template>
  <div class="submit-form">
    <div v-if="!submitted">
      <div class="form-group">
        <label for="name">Name</label>
        <input
          type="text"
          class="form-control"
          id="name"
          required
          v-model="product.name"
          name="name"
        />
      </div>

      <div class="form-group">
        <label for="description">Description</label>
        <input
          class="form-control"
          id="description"
          required
          v-model="product.description"
          name="description"
        />
      </div>

      <div class="form-group">
        <label for="price">¨Price</label>
        <input
          class="form-control"
          id="price"
          required
          v-model="product.price"
          name="price"
        />
      </div>

      <button @click="saveProduct" class="btn btn-success">Create</button>
    </div>

    <div v-else>
      <h4>You added a product successfully!</h4>
      <button class="btn btn-success" @click="newProduct">Add</button>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import ProductDataService from "@/services/ProductDataService";
import Product from "@/types/Product";
import ResponseData from "@/types/ResponseData";

export default defineComponent({
  name: "add-product",
  data() {
    return {
      product: {
        id: null,
        name: "",
        description: "",
        price: 0,
      } as Product,
      submitted: false,
    };
  },
  methods: {
    saveProduct() {
      let data = {
        name: this.product.name,
        description: this.product.description,
        price: this.product.price,
      };

      ProductDataService.create(data)
        .then((response: ResponseData) => {
          this.product.id = response.data.id;
          console.log(response.data);
          this.submitted = true;
        })
        .catch((e: Error) => {
          console.log(e);
        });
    },

    newProduct() {
      this.submitted = false;
      this.product = {} as Product;
    },
  },
});
</script>

<style>
.submit-form {
  max-width: 300px;
  margin: auto;
}
</style>
