export default function StateService() {
    'ngInject';

    const states = {
        HOME: 'home state',
        RECEPT_LIST: 'recept list',
        RECEPT_DESCRIPTION: 'recept description',
        RECEPT_EDIT: 'recept edit page',
        RECEPT_CREATE: 'recept create page',
        INGRIDIENT_EDIT: 'ingridient edit'
    };

    const vm = this;

    Object.assign(vm, {
        states
    });

}
