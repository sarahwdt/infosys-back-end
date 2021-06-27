<template>
  <div>
    <div v-if="!loading">
      <div v-if="auth.hasPrivilege(read_privilege)" class="w3-bar w3-teal w3-container w3-left-align w3-large">
        <a v-if="auth.hasPrivilege(write_privilege)" v-on:click="modalCreate" class="w3-bar-item w3-button w3-hide-small w3-hover-white ">Добавить</a>
        <a v-if="modal.id !== 0 && auth.hasPrivilege(write_privilege)" v-on:click="modalUpdate" class="w3-bar-item w3-button w3-hide-small w3-hover-white"
        >Редактировать</a>
        <a v-if="modal.id !== 0" v-on:click="modalShow" class="w3-bar-item w3-button w3-hide-small w3-hover-white"
        >Открыть</a>
        <a v-if="modal.id !== 0 && auth.hasPrivilege(write_privilege)" v-on:click="deleteRequest" class="w3-bar-item w3-button w3-hide-small w3-hover-white ">Удалить</a>
      </div>


      <modal v-if="modal.show" @close="rollbackModal" v-bind:title="title" v-bind="modal"
             class="w3-animate-opacity"></modal>


      <table class="w3-table-all w3-animate-opacity">

        <thead>
        <tr class="w3-border">
          <th v-for="column in displayedColumns" :key="column.title"><a
              v-on:click="getPage({sort:column.key})">{{ column.title }}</a></th>
        </tr>
        </thead>
        <tbody>
        <tr v-on:click="modal.id = item.id" :class="{'w3-teal':modal.id === item.id}" class="w3-hover-teal w3-border"
            v-for="item in items" :key="item.id">
          <td v-for="column in displayedColumns" :key="column.title">
            {{ displayedData(column, item) }}
          </td>
        </tr>
        </tbody>
      </table>

      <div class="w3-bar w3-center w3-margin-top w3-animate-opacity">
        <a class="w3-button w3-hover-teal" v-if="thisPage.page > 1" v-on:click="getPage({page : 1})"> 1 </a>
        <span v-if="thisPage.page > 2">...</span>
        <a class="w3-button w3-hover-teal" v-if="thisPage.page > 0"
           v-on:click="getPage({page : thisPage.page - 1})">{{ thisPage.page }}</a>
        <a class="w3-button w3-teal w3-border">{{ thisPage.page + 1 }}</a>
        <a class="w3-button w3-hover-teal" v-if="thisPage.page < total - 1"
           v-on:click="getPage({page : thisPage.page + 1})">{{ thisPage.page + 2 }}</a>
        <span v-if="thisPage.page < total - 3">...</span>
        <a class="w3-button w3-hover-teal" v-if="thisPage.page < total - 2" v-on:click="getPage({page: total - 1})"> {{
            total
          }}</a>
      </div>
    </div>
    <div v-if="loading" class="w3-container loader w3-display-middle"></div>
  </div>
</template>

<script>
import {auth, axios} from "@/_helpers";
import CrudModal from "@/components/modal/CrudModal";

export default {
  name: "ListPage",
  components: {
    'modal': CrudModal
  },
  data() {
    return {
      items: [],
      total: '',
      loading: false,
      auth: auth,
      modal: {
        show: false,
        readOnly: true,
        id: 0,
        fields: this.fields,
        url: this.url
      },
      thisPage: {
        page: 0,
        size: 10,
        sort: 'id',
        dir: 'asc'
      }
    }
  },
  props: {
    url: String,
    read_privilege: String,
    write_privilege:String,
    title:String,
    columns: Array[
        {
          title: String,
          key: String,
          displayedData: Function,
          displayIf: Function
        }
        ],
    fields: Array
  },
  created() {
    this.updateData()
  },
  watch: {
    fields: {
      deep: true,
      handler() {
        this.modal.fields = this.fields
      }
    },
    url: {
      handler() {
        this.thisPage.page = 0
        this.thisPage.sort = 'id'
        this.thisPage.dir = 'asc'
        this.rollbackModal()
        this.updateData()
      }
    }
  },
  computed: {
    displayedColumns: function () {
      return this.columns.filter((column) => column.displayIf === undefined || column.displayIf())
    }
  },
  methods: {
    getPage({page, size, sort}) {

      if (page !== undefined) this.thisPage.page = page
      if (size !== undefined) this.thisPage.size = size
      if (sort !== undefined) {
        if (sort === this.thisPage.sort) if (this.thisPage.dir === 'asc') this.thisPage.dir = 'desc'
        else this.thisPage.dir = 'asc'
        this.thisPage.sort = sort
      }
      this.updateData()
    },
    updateData() {
      this.loading = true
      axios.get(this.url, {
        params: {
          page: this.thisPage.page,
          size: this.thisPage.size,
          sort: this.thisPage.sort + ',' + this.thisPage.dir
        }
      }).then(response => {
        this.items = response.data.content
        this.total = response.data.totalPages
      }).finally(() => this.loading = false)
    },
    rollbackModal() {
      this.modal.id = 0
      this.modal.readOnly = false
      this.modal.show = false
      this.modal.config = this.fields
      this.modal.url = this.url
      this.updateData()
    },
    modalUpdate() {
      this.modal.readOnly = false
      this.modal.show = true
      this.modal.config = this.fields
      this.modal.url = this.url
    },
    modalCreate() {
      this.modal.id = 0
      this.modal.readOnly = false
      this.modal.show = true
      this.modal.config = this.fields
      this.modal.url = this.url
    },
    modalShow() {
      this.modal.readOnly = true
      this.modal.show = true
      this.modal.config = this.fields
      this.modal.url = this.url
    },
    deleteRequest() {
      axios.delete(this.url + '/' + this.modal.id)
      this.updateData()
    },
    displayedData(column, item) {
      return column.displayedData !== undefined ? column.displayedData(item) : item[column.key]
    }
  }
}
</script>

<style scoped>
.loader {
  border: 16px solid #f3f3f3; /* Light grey */
  border-top: 16px solid teal; /* Blue */
  border-radius: 50%;
  width: 120px;
  height: 120px;
  animation: spin 0.3s linear infinite;
}

@keyframes spin {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}
</style>