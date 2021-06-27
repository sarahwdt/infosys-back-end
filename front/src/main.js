import Vue from 'vue';

import {router, store} from './_helpers';
import App from "@/./App";
import BootstrapVue from 'bootstrap-vue'
import {auth} from "@/_helpers/auth";

Vue.use(BootstrapVue);

new Vue({
    el: '#app',
    router,
    store,
    created() {
        auth.update()
    },
    render: h => h(App)
});