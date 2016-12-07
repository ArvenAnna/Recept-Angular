export default function ReceptDescription() {
    'ngInject';

    return {
        controller: ReceptDescriptionController,
        controllerAs: 'receptDescriptionCtrl',
        templateUrl: '/frontend/html/content/recept-description.html',
        bindings: {
            receptId: '=',
            state: '='
        }
    };
}

export function ReceptDescriptionController($q, contentService,
                                            stateService,
                                            receptDescriptionService) {

    const vm = this;

    Object.assign(vm, {
        $onInit,
        editRecept,
        deleteRecept,
        viewReference,
        getPdfFromRecept
    });

    function $onInit() {
        getReceptInformation();
    }

    function getPdfFromRecept() {
        receptDescriptionService.getPdfFromRecept(vm.receptId)
            .then(data => vm.pdf = URL.createObjectURL(new Blob([data.data])))
            .catch(reason => vm.error = reason);
    }

    function editRecept() {
        revokeObjectUrls();
        vm.state = stateService.states.RECEPT_EDIT;
    }

    function deleteRecept() {
        revokeObjectUrls();
        receptDescriptionService.deleteRecept(vm.recept.id);
        vm.state = stateService.states.RECEPT_LIST;
    }

    function viewReference(receptId) {
        revokeObjectUrls();
        vm.receptId = receptId;
        getReceptInformation();
    }

    function revokeObjectUrls() {
        URL.revokeObjectURL(vm.pdf);
        URL.revokeObjectURL(vm.recept.file);
        vm.recept.details.forEach(detail => URL.revokeObjectURL(detail.file));
    }

    function getReceptInformation() {
        if (vm.receptId) {
            let receptPromise = contentService.getRecept(vm.receptId);
            let filePromise = contentService.getReceptFile(vm.receptId);

            $q.all([receptPromise, filePromise]).then(values => {
                vm.recept = values[0];
                vm.recept.file = contentService.getUrlFromPngFile(values[1]);
                vm.recept.details.forEach((detail)=> {
                    contentService.getDetailFile(detail.id)
                        .then((data)=> {
                            detail.file = contentService.getUrlFromPngFile(data);
                        });
                });
            });
        }
    }
}