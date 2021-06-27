import {auth} from "@/_helpers";
import ObjectMultiselect from "@/components/fragments/ObjectMultiselect";

export const usersListConfig = {
    url: '/user',
    read_privilege: 'READ_USER',
    write_privilege: 'WRITE_USER',
    title: "Пользователь",
    columns: [
        {
            title: 'Идентификатор',
            key: 'id'
        },
        {
            title: 'Логин',
            key: 'login'
        },
        {
            title: 'Роли',
            key: 'roles',
            displayedData(item) {
                return item.roles.map(role => role.name)||''
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
    fields: [
        {
            title: 'Логин',
            key: 'login',
            disabled: 'true'
        },
        {
            title: 'Роли',
            key: 'roles',
            component: ObjectMultiselect,
            props(){
                return{
                    url:'/role',
                    displayedValue(item){
                        return item['name']
                    },
                }
            }
        }
    ]
}