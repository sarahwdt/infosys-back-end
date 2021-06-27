import {auth} from "@/_helpers";

export const privilegesListConfig = {
    url: '/privilege',
    read_privilege: 'READ_PRIVILEGE',
    write_privilege: 'WRITE_PRIVILEGE',
    title: "Привилегия",
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
                if(target === undefined || target === '') return 'У привилегии должно быть задано название'
            }
        }
    ]
}