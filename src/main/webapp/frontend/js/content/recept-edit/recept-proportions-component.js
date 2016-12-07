export default function ReceptProportions() {
    'ngInject';

    return {
        controller: ReceptProportionsController,
        controllerAs: 'receptProportionsCtrl',
        templateUrl: '/frontend/html/content/edit/recept-proportions.html',
        bindings: {
            receptId: '<',
            model: '='
        }
    };
}

export function ReceptProportionsController(contentService,
                                            receptEditService) {

    const vm = this;

    Object.assign(vm, {
        $onInit,
        saveProportion,
        updateProportion,
        deleteProportion,
        chooseIngridient,

        proportion: null
    });

    function chooseIngridient() {
        if (!vm.proportion.ingridient.name) {
            return;
        }
        let arr = vm.ingridients.filter(isValidIngridient);
        if (arr.length == 0) {
            setEmptyProportion();
        }
    }

    function setEmptyProportion() {
        vm.proportion = {
            norma: null,
            ingridient: {
                id: null,
                name: null
            }
        }
    }

    function deleteProportion(propId) {
        receptEditService.deleteProportion(propId)
            .then(setProportionsFromServer);
    }

    function updateProportion(propId) {
        vm.model.proportions.forEach((prop)=> {
            if (prop.id === propId) {
                receptEditService.saveProportion(prop, vm.receptId)
                    .then(setProportionsFromServer);
                return;
            }
        });
    }

    function isValidIngridient(value) {
        return value.name.toLowerCase() == vm.proportion.ingridient.name.toLowerCase();
    }

    function saveProportion() {
        if (vm.proportion.ingridient.name) {
            let arr = vm.ingridients.filter(isValidIngridient);
            if (arr.length == 0) {
                return;
            }
            vm.proportion.ingridient.id = arr[0].id;

            receptEditService.saveProportion(vm.proportion, vm.receptId)
                .then(()=> {
                    setEmptyProportion();
                    setProportionsFromServer();
                });
        }
    }

    function $onInit() {
        setEmptyProportion();
        contentService.getIngridients()
            .then(data => vm.ingridients = data)
    }

    function setProportionsFromServer() {
        receptEditService.getReceptsProportions(vm.receptId)
            .then(data => vm.model.proportions = data);
    }

}
