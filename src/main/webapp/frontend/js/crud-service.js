export default function CrudService($http, $q) {
    'ngInject';

    const urls = {
        GET_RECEPT: 'recept.req',
        GET_RECEPT_LIST_BY_DEPART: 'recept_list.req',
        GET_RECEPT_LIST_BY_TAG: 'recept_list_tag.req',
        DELETE_RECEPT: 'recept.req',
        PUT_RECEPT: 'recept.req',
        POST_RECEPT: 'recept.req',
        POST_PROPORTION: 'proportion.req',
        GET_PROPORTIONS: 'proportions.req',
        DELETE_PROPORTION: 'proportion.req',
        POST_DETAIL: 'detail.req',
        GET_DETAILS: 'details.req',
        DELETE_DETAIL: 'detail.req',
        POST_REFERENCE: 'reference.req',
        GET_REFERENCES: 'references.req',
        DELETE_REFERENCE: 'reference.req',
        POST_CATEGORY: 'category.req',
        GET_CATERORIES: 'categories.req',
        DELETE_CATEGORY: 'category.req',
        POST_INGRIDIENT: 'ingridient.req',
        DELETE_INGRIDIENT: 'ingridient.req',
        GET_INGRIDIENTS: 'ing_list.req',
        GET_DEPARTS: 'departs.req',
        GET_TAGS: 'tags.req',

        FILE_RECEPT_SAVE: 'file.req',
        FILE_RECEPT_GET: 'file.req',
        FILE_DETAIL_SAVE: 'detail_file.req',
        FILE_DETAIL_GET: 'detail_file.req',
        GET_PDF_FILE: 'pdfFile.req',
        PARSE_XML: 'xmlFile.req'
    };

    Object.assign(this, {
        doGet,
        doPost,
        doDelete,
        doPut,
        transformResponse,
        urls: urls
    });

    function doGet(url, params) {
        let response;
        if (params) {
            response = $http.get(url, {
                params: params
            });
        } else {
            response = $http.get(url);
        }
        return transformResponse(response);
    }

    function doDelete(url, params) {
        return transformResponse($http.delete(url, {
            params: params
        }));
    }

    function doPost(url, object, params) {
        let response;
        if (params) {
            response = $http.post(url, object, {
                params: params
            });
        } else {
            response = $http.post(url, object);
        }
        return transformResponse(response);
    }

    function doPut(url, object, params) {
        let response;
        if (params) {
            response = $http.put(url, object, {
                params: params
            });
        } else {
            response = $http.put(url, object);
        }
        return transformResponse(response);
    }

    function transformResponse(response) {
        return response
            .then((response) => response.data)
            .catch((response) => $q.reject(response.data.message));
    }
}