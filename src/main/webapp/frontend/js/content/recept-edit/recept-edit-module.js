import ReceptEdit from './recept-edit-component';
import ReceptMainInformation from './recept-main-information-component';
import ReceptProportions from './recept-proportions-component';
import ReceptDetails from './recept-details-component';
import ReceptReferences from './recept-references-component';
import ReceptTags from './recept-tags-component';
import ReceptEditService from './recept-edit-service';

const MODULE_NAME = 'edit';

const module = angular.module(MODULE_NAME, []);

module
    .component('receptEdit', new ReceptEdit())
    .component('receptMainInformation', new ReceptMainInformation())
    .component('receptProportions', new ReceptProportions())
    .component('receptDetails', new ReceptDetails())
    .component('receptTags', new ReceptTags())
    .component('receptReferences', new ReceptReferences())
    .service('receptEditService', ReceptEditService);

export {module, MODULE_NAME as default};
