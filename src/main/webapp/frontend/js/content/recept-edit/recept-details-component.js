export default function ReceptDetails() {
    'ngInject';

    return {
        controller: ReceptDetailsController,
        controllerAs: 'receptDetailsCtrl',
        templateUrl: '/frontend/html/content/edit/recept-details.html',
        bindings: {
            receptId: '<',
            model: '='
        }
    };
}

export function ReceptDetailsController(contentService,
                                        receptEditService) {

    const vm = this;

    Object.assign(vm, {
        $onInit,
        saveDetail,
        deleteDetail,
        updateDetail,

        description: null
    });

    function $onInit() {
        setDetailsFromServer();
    }

    function saveDetail() {
        revokeObjectUrls();
        receptEditService.saveDetail(vm.detail, vm.receptId)
            .then(id => {
                saveFile(id).then(setDetailsFromServer);
                vm.detail.description = null;
            });
    }

    function saveFile(detailId) {
        if (vm.foto) {
            return contentService.saveDetailFile(vm.foto, detailId)
                .then(()=> vm.foto = null);
        }
        return new Promise(()=> {
        });
    }

    function deleteDetail(detId) {
        revokeObjectUrls();
        receptEditService.deleteDetail(detId)
            .then(setDetailsFromServer);
    }

    function updateDetail(detId) {
        revokeObjectUrls();
        let detail = vm.model.details.filter(detail => detail.id === detId)[0];
        receptEditService.saveDetail(detail, vm.receptId)
            .then(setDetailsFromServer);

    }

    function setDetailsFromServer() {
        receptEditService.getReceptsDetails(vm.receptId)
            .then(data => {
                vm.model.details = data;
                vm.model.details.forEach(detail => contentService.getDetailFile(detail.id)
                    .then(file => detail.file = contentService.getUrlFromPngFile(file)));
            });
    }

    function revokeObjectUrls() {
        vm.model.details.forEach(detail => URL.revokeObjectURL(detail.file));
    }

}

