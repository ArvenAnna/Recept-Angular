export default function Sidebar() {
    'ngInject';

    return {
        controller: SidebarController,
        controllerAs: 'sidebarCtrl',
        templateUrl: '/frontend/html/sidebar/sidebar.html',
        bindings: {
            state: '=',
            query: '=',
            sort: '=',
            itemsPerPage: '=',
            receptId: '='
        }
    };
}

export function SidebarController(stateService, sidebarService) {

    const vm = this;

    Object.assign(vm, {
        createRecept,
        editIngridients,
        parseFile,
        isUploadXmlMode
    });

    function isUploadXmlMode() {
        return !vm.file;
    }

    function parseFile() {
        if (vm.file) {
            sidebarService.parseReceptFromXML(vm.file)
                .then(data=> {
                    vm.file = null;
                    vm.receptId = data;
                    vm.state = stateService.states.RECEPT_DESCRIPTION;

                })
                .catch(reason => {
                    //vm.file = null;
                    vm.error = reason;
                });
        }
    }

    function createRecept() {
        vm.state = null; // for reseting data between two create states;
        vm.state = stateService.states.RECEPT_CREATE;
    }

    function editIngridients() {
        vm.state = stateService.states.INGRIDIENT_EDIT;
    }

}