import { createWebHistory, createRouter } from "vue-router";
import { RouteRecordRaw } from "vue-router";

const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    alias: "/product",
    name: "product",
    component: () => import("./components/ProductList.vue"),
  },
  {
    path: "/product/:id",
    name: "¨product-details",
    component: () => import("./components/ProductDetails.vue"),
  },
  {
    path: "/add",
    name: "add",
    component: () => import("./components/AddProduct.vue"),
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
