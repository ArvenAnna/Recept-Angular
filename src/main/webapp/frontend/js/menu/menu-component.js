export default function Menu() {
    'ngInject';

    return {
        controller: MenuController,
        controllerAs: 'menuCtrl',
        templateUrl: '/frontend/html/menu/menu.html',
        bindings: {
            theme: '=',
            choosenDepartId: '=',
            choosenTagId: '=',
            state: '='
        }
    };
}

export function MenuController($q, menuService, stateService) {

    const vm = this;

    Object.assign(vm, {
        $onInit,
        chooseDepart,
        chooseTag,
        departs: []
    });

    function $onInit() {
        $q.all([menuService.getDepartments(), menuService.getTags()])
            .then(values => {
                vm.departs = values[0];
                vm.tags = values[1];
                console.log(values);
            });
    }

    function chooseDepart(id) {
        vm.state = stateService.states.RECEPT_LIST;
        vm.choosenDepartId = id;
    }

    function chooseTag(id) {
        vm.state = stateService.states.RECEPT_LIST;
        vm.choosenTagId = id;
    }
}