export default function ReceptReferences() {
    'ngInject';

    return {
        controller: ReceptReferencesController,
        controllerAs: 'receptReferencesCtrl',
        templateUrl: '/frontend/html/content/edit/recept-references.html',
        bindings: {
            receptId: '<',
            model: '=',
        }
    };
}

export function ReceptReferencesController(contentService,
                                           receptListService,
                                           receptEditService) {

    const vm = this;

    Object.assign(vm, {
        $onInit,
        deleteReference,
        saveReference,
        chooseReference,
        setEmptyReference
    });

    function chooseReference() {
        if (!vm.reference.receptReferenceName) {
            return;
        }
        let arr = vm.recepts.filter(value => value.name == vm.reference.receptReferenceName);
        if (arr.length == 0) {
            setEmptyReference();
        }
    }

    function setEmptyReference() {
        vm.reference = {
            receptReferenceId: null,
            receptReferenceName: null
        }
    }

    function $onInit() {
        setEmptyReference();
        receptListService.getReceptList(-1)
            .then(data => vm.recepts = data);
    }

    function saveReference() {
        if (vm.reference.receptReferenceName) {
            let arr = vm.recepts.filter(value => value.name == vm.reference.receptReferenceName);
            if (arr.length == 0) {
                return;
            }
            vm.reference.receptReferenceId = arr[0].id;

            receptEditService.saveReference(vm.reference, vm.receptId)
                .then(() => {
                    setEmptyReference();
                    setReferencesFromServer();
                });
        }
    }

    function setReferencesFromServer() {
        receptEditService.getReceptsReferences(vm.receptId)
            .then(data => vm.model.references = data);
    }

    function deleteReference(refId) {
        receptEditService.deleteReference(refId)
            .then(setReferencesFromServer);
    }

}