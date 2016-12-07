export default function ReceptEditService(crudService) {
    'ngInject';

    Object.assign(this, {
        saveRecept,
        saveUniqueRecept,
        saveProportion,
        getReceptsProportions,
        deleteProportion,
        saveDetail,
        getReceptsDetails,
        deleteDetail,
        saveReference,
        getReceptsReferences,
        deleteReference,
        saveReceptsTag,
        getReceptsTags,
        deleteTagFromRecept
    });

    function saveRecept(recept) {
        return crudService
            .doPut(crudService.urls.PUT_RECEPT, recept);
    }

    function saveUniqueRecept(recept) {
        return crudService
            .doPost(crudService.urls.POST_RECEPT, recept);
    }

    function saveProportion(proportion, receptId) {
        return crudService
            .doPost(crudService.urls.POST_PROPORTION, proportion, {
                    receptId: receptId
                }
            );
    }

    function getReceptsProportions(receptId) {
        return crudService.doGet(crudService.urls.GET_PROPORTIONS, {
            receptId: receptId
        });
    }

    function deleteProportion(propId) {
        return crudService
            .doDelete(crudService.urls.DELETE_PROPORTION, {
                propId: propId
            });
    }

    function saveDetail(detail, receptId) {
        detail.receptId = receptId;
        return crudService.doPost(crudService.urls.POST_DETAIL, detail);
    }

    function getReceptsDetails(receptId) {
        return crudService.doGet(crudService.urls.GET_DETAILS, {
            receptId: receptId
        });
    }

    function deleteDetail(detId) {
        return crudService
            .doDelete(crudService.urls.DELETE_DETAIL, {
                detId: detId
            });
    }

    function saveReference(reference, receptId) {
        return crudService
            .doPost(crudService.urls.POST_REFERENCE, reference, {
                receptId: receptId
            });
    }

    function getReceptsReferences(receptId) {
        return crudService.doGet(crudService.urls.GET_REFERENCES, {
            receptId: receptId
        });
    }

    function deleteReference(refId) {
        return crudService
            .doDelete(crudService.urls.DELETE_REFERENCE, {
                refId: refId
            });
    }

    function saveReceptsTag(tagId, receptId) {
        return crudService
            .doPost(crudService.urls.POST_CATEGORY, receptId, {
                tagId: tagId
            });
    }

    function getReceptsTags(receptId) {
        return crudService
            .doGet(crudService.urls.GET_CATERORIES, {
                receptId: receptId
            });
    }

    function deleteTagFromRecept(tagId, receptId) {
        return crudService
            .doDelete(crudService.urls.DELETE_CATEGORY, {
                receptId: receptId,
                tagId: tagId
            });
    }

}


