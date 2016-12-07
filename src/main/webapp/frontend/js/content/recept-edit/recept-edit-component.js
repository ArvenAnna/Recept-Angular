export default function ReceptEdit() {
    'ngInject';

    return {
        controller: ReceptEditController,
        controllerAs: 'receptEditCtrl',
        templateUrl: '/frontend/html/content/recept-edit.html',
        bindings: {
            receptId: '=',
            state: '='
        }
    };
}

export function ReceptEditController(receptModel,
                                     contentService,
                                     stateService) {

    const vm = this;

    Object.assign(vm, {
        $onInit,
        openProportions,
        hideMainInformation,
        openMainInformation,
        hideProportions,
        openDetails,
        hideDetails,
        openReferences,
        hideReferences,
        openTags,
        hideTags,
        finish,

        model: receptModel,

        proportionVisible: false,
        mainInformationVisible: true,
        detailsVisible: false,
        referencesVisible: false,
        tagsVisible: false,
        belowContentVisible: false
    });

    function finish() {
        vm.state = stateService.states.RECEPT_DESCRIPTION;
    }

    function openMainInformation() {
        vm.mainInformationVisible = true;
    }

    function openProportions() {
        vm.proportionVisible = true;
    }

    function openDetails() {
        vm.detailsVisible = true;
    }

    function openReferences() {
        vm.referencesVisible = true;
    }

    function openTags() {
        vm.tagsVisible = true;
    }

    function hideTags() {
        vm.tagsVisible = false;
    }

    function hideReferences() {
        vm.referencesVisible = false;
    }

    function hideDetails() {
        vm.detailsVisible = false;
    }

    function hideMainInformation() {
        vm.mainInformationVisible = false;
    }

    function hideProportions() {
        vm.proportionVisible = false;
    }

    function $onInit() {
        if(vm.receptId) {
            contentService.getRecept(vm.receptId)
                .then(data => {
                   vm.model = data;
                   vm.receptId = data.id;
                });
        }
    }
}