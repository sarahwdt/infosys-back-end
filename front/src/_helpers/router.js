import Vue from 'vue';
import Router from 'vue-router';

import HomePage from "@/components/HomePage"
import PrivilegePage from "@/components/ListPage";
import {
    clientsListConfig,
    instructorsListConfig,
    privilegesListConfig,
    rolesListConfig,
    sessionsListConfig, usersListConfig
} from "@/_page-configs";
import {store} from "@/_helpers/store";

Vue.use(Router);

const checkAuth = function (to, from, next) {
    if (!store.state.user) {
        next('/login');
    } else {
        next();
    }
}

export const router = new Router({
    mode: 'history',
    routes: [
        {path: '/', component: HomePage},
        {path: '*', redirect: '/'},
        {
            path:'/privilege',
            component: PrivilegePage,
            beforeEnter: checkAuth,
            props: privilegesListConfig
        },
        {
            path:'/role',
            component: PrivilegePage,
            beforeEnter: checkAuth,
            props: rolesListConfig
        },
        {
            path:'/user',
            component: PrivilegePage,
            beforeEnter: checkAuth,
            props: usersListConfig
        },
        {
            path:'/client',
            component: PrivilegePage,
            beforeEnter: checkAuth,
            props: clientsListConfig
        },
        {
            path:'/session',
            component: PrivilegePage,
            beforeEnter: checkAuth,
            props: sessionsListConfig
        },
        {
            path:'/instructor',
            component: PrivilegePage,
            beforeEnter: checkAuth,
            props: instructorsListConfig
        },

    ],
});

