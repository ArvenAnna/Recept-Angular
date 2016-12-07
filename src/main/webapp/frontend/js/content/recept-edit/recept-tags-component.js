export default function ReceptTags() {
    'ngInject';

    return {
        controller: ReceptTagsController,
        controllerAs: 'receptTagsCtrl',
        templateUrl: '/frontend/html/content/edit/recept-tags.html',
        bindings: {
            receptId: '<',
            model: '=',
        }
    };
}

export function ReceptTagsController(menuService,
                                     receptEditService) {

    const vm = this;

    Object.assign(vm, {
        $onInit,
        saveTag,
        deleteTag,

        tagId: null
    });

    function $onInit() {
        menuService.getTags()
            .then(data => vm.tags = data);
    }

    function saveTag() {
        receptEditService.saveReceptsTag(vm.tagId, vm.receptId)
            .then(() => {
                vm.tagId = null;
                setTagsFromServer();
            });
    }

    function setTagsFromServer() {
        receptEditService.getReceptsTags(vm.receptId)
            .then(data => vm.model.tags = data);
    }

    function deleteTag(tagId) {
        receptEditService.deleteTagFromRecept(tagId, vm.receptId)
            .then(setTagsFromServer);
    }

}
