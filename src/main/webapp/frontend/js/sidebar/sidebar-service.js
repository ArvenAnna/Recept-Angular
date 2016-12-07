export default function SidebarService($http, $q, crudService) {
    'ngInject';

    Object.assign(this, {
        parseReceptFromXML
    });

    function parseReceptFromXML(file) {
        let fd = new FormData();
        fd.append('file', file);
        return crudService.transformResponse($http.post(crudService.urls.PARSE_XML, fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        }));
    }
}
