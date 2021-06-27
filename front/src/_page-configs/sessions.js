import {auth} from "@/_helpers";
import DateTimePicker from "@/components/fragments/DateTimePicker";
import ObjectSelect from "@/components/fragments/ObjectSelect";
import ObjectMultiselect from "@/components/fragments/ObjectMultiselect";

export const sessionsListConfig = {
    url: '/session',
    read_privilege: 'READ_SESSION',
    write_privilege: 'WRITE_SESSION',
    title: 'Занятие',
    columns: [
        {
            title:'Идентификатор',
            key:'id'
        },
        {
            title:'Имя инструктора',
            key:'instructor',
            displayedData: (item) =>{
                return item.instructor.secondName + " " + item.instructor.firstName
            }
        },
        {
            title:'Дата',
            key:'date',
            displayedData: (item) =>{
                return new Date(Date.parse(item.date)).toLocaleString()
            }
        },
        {
            title:'Комментарий',
            key:'comment'
        },
        {
            title: 'Создал',
            key: 'createdBy',
            displayIf: function () {
                return auth.hasPrivilege('READ_HISTORY')
            },
            displayedData(item) {
                return item['createdBy']
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
            title:'Дата',
            key:'date',
            component: DateTimePicker,
            validateFunction: function (target){
                if(target === undefined) return 'Необходимо установить дату занятия'
                if((new Date(target)) < Date.now()) return 'Занятие не может быть проведено в прошлом'
            }
        },
        {
            title:'Инструктор',
            key:'instructor',
            component: ObjectSelect,
            props(){
                return{
                    url:'/instructor',
                    displayedValue(item){
                        return item['secondName'] + ' ' + item['firstName']
                    }
                }
            },
            validateFunction(target){
                if(target === undefined || target.id === undefined) return 'На занятие должен быть назначен инструктор'
            }

        },
        {
            title:'Клиенты',
            key:'clients',
            component: ObjectMultiselect,
            props() {
                return {
                    url: '/client',
                    displayedValue(item) {
                        return item['secondName'] + ' ' + item['firstName'];
                    }
                };
            },
            validateFunction: function (target){
                if(target === undefined) return 'На занятие необходимо назначить клиентов'
                if(target.length < 1) return 'На занятии должен присутствовать хотя бы один клиент'
            }
        },
        {
            title:'Комментарий',
            key:'comment'
        },
    ]
}