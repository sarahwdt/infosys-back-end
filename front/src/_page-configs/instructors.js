import {auth} from "@/_helpers";

export const instructorsListConfig = {
    url: '/instructor',
    read_privilege: 'READ_INSTRUCTOR',
    write_privilege: 'WRITE_INSTRUCTOR',
    title: 'Инструктор',
    columns: [
        {
            title: 'Идентификатор',
            key: 'id'
        },
        {
            title: 'Имя',
            key: 'secondName',
            displayedData: (item) => {
                return item.secondName + " " + item.firstName
            }
        },
        {
            title: 'Телефон',
            key: 'phone'
        },
        {
            title: 'e-mail',
            key: 'email'
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
            title: 'Имя',
            key: 'firstName',
            validateFunction(target) {
                if (target === undefined || target.length < 2) return 'Имя должно содержать больше 2х символов'
            }
        },
        {
            title: 'Отчество',
            key: 'middleName',
        },
        {
            title: 'Фамилия',
            key: 'secondName',
            validateFunction(target) {
                if (target === undefined || target.length < 2) return 'Фамилия должна содержать больше 2х символов'
            }
        },
        {
            title: 'Телефон',
            key: 'phone',
            validateFunction(target) {
                if (target === undefined || target.length !== 12) return 'Номер телефона в формате +7XXXXXXXXXXX'
            }
        },
        {
            title: 'e-mail',
            key: 'email',
            validateFunction(target) {
                const validRegex = /^[\w!#$%&'*+/=?`{|}~^-]+(?:\.[\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\.)+[a-zA-Z]{2,6}$/
                if (target === undefined || !target.match(validRegex))
                    return 'E-mail должен быть в формате example@mail.com'
            }
        },
    ]
}