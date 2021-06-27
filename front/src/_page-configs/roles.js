import {auth} from "@/_helpers";
import ObjectMultiselect from "@/components/fragments/ObjectMultiselect";

export const rolesListConfig = {
    url: '/role',
    title: "Роль",
    read_privilege: 'READ_ROLE',
    write_privilege: 'WRITE_ROLE',
    columns: [
        {
            title:'Идентификатор',
            key:'id'
        },
        {
            title:'Название',
            key:'name'
        },
        {
          title: 'Привилегии',
          key:'privileges',
          displayedData(item) {
              return item.privileges.map(privilege => privilege['name']);
          }
        },
        {
            title: 'Создал',
            key: 'createdBy',
            displayIf: function () {
                return auth.hasPrivilege('READ_HISTORY')
            },
            displayedData(item) {
                return item['createdBy'] && item['createdBy']['login']
            }
        },
        {
            title: 'Дата создания',
            key: 'createdDate',
            displayIf: function () {
                return auth.hasPrivilege('READ_HISTORY')
            },
        },
        {
            title: 'Редактировал',
            key: 'lastModifiedBy',
            displayIf: function () {
                return auth.hasPrivilege('READ_HISTORY')
            },
            displayedData(item) {
                return item['lastModifiedBy'] && item['lastModifiedBy']['login']
            }
        },
        {
            title: 'Дата редактирования',
            key: 'lastModifiedDate',
            displayIf: function () {
                return auth.hasPrivilege('READ_HISTORY')
            },
        },
    ],
    fields:[
        {
            title:'Название',
            key:'name',
            validateFunction(target){
                if(target === undefined || target === '') return 'У роли должно быть задано название'
            }
        },
        {
            title: 'Привилегии',
            key: 'privileges',
            component: ObjectMultiselect,
            props(){
                return{
                    url:"/privilege",
                    displayedValue(item){
                        return item['name']
                    }
                }
            },
            validateFunction(target){
                if(target === undefined || target.length < 1) return 'Роли должны быть назначены привилегии'
            }
        }
    ]
}