import Axios from "axios";
import {store} from "@/_helpers/store";
import {router} from "@/_helpers/router";

export const axios = new Axios.create({
    baseURL: '/api'
})

axios.defaults.headers.common["Content-Type"] = "application/json"
axios.interceptors.response.use((responce) => responce, (error) =>{
    if(parseInt(error.response.status) === 401) router.push('/')
    if(parseInt(error.response.status) === 500) router.push('/')

    store.commit('error', error.response.data.error)
    throw error;
});