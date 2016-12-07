export default function ReceptMainInformation() {
    'ngInject';

    return {
        controller: ReceptMainInformationController,
        controllerAs: 'receptMainInformationCtrl',
        templateUrl: '/frontend/html/content/edit/recept-main-information.html',
        bindings: {
            receptId: '=',
            model: '=',
            belowContentVisible: '='
        }
    };
}

export function ReceptMainInformationController(menuService,
                                                contentService,
                                                receptEditService) {

    const vm = this;

    Object.assign(vm, {
        $onInit,
        isSaveMainInfoVisible,
        saveMainInfo
    });

    function $onInit() {
        menuService.getDepartments()
            .then(data => vm.departs = data);
        if (vm.receptId) {
            contentService.getReceptFile(vm.receptId)
                .then(data => vm.file = contentService.getUrlFromPngFile(data));
        }
    }

    function saveMainInfo() {
        if (vm.model.id) {
            contentService.getRecept(vm.model.id)
                .then(data => {
                    if (vm.model.name === data.name) {
                        receptEditService.saveRecept(vm.model)
                            .then(processSaveResponse);
                    } else {
                        saveUniqueRecept();
                    }
                });

        } else {
            saveUniqueRecept();
        }

    }

    function saveUniqueRecept() {
        receptEditService.saveUniqueRecept(vm.model)
            .then(processSaveResponse)
            .catch(reason => vm.error = reason);
    }

    function isSaveMainInfoVisible() {
        return vm.model.name && vm.model.departId.id
    }

    function processSaveResponse(data) {
        revokeObjectUrl();
        vm.belowContentVisible = true;
        vm.receptId = data;
        saveFile();
    }

    function saveFile() {
        if (vm.foto) {
            contentService.saveReceptFile(vm.foto, vm.receptId)
                .then(data => {
                    vm.file = contentService.getUrlFromPngFile(data);
                    vm.foto = null;
                });
        }
    }

    function revokeObjectUrl() {
        URL.revokeObjectURL(vm.file);
    }

}
