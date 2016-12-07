import Content from './content-component';
import ContentService from './content-service';
import ReceptList from './recept-list/recept-list-component';
import ReceptListService from './recept-list/recept-list-service'
import ReceptDescription from './recept-description/recept-description-component';
import ReceptDescriptionService from './recept-description/recept-description-service';
import Ingridients from './ingridients/ingridients-component';
import IngridientsService from './ingridients/ingridients-service';
import ReceptEditModule from './recept-edit/recept-edit-module';

const MODULE_NAME = 'content';

const module = angular.module(MODULE_NAME, [ReceptEditModule]);

module
    .component('receptContent', new Content())
    .service('contentService', ContentService)
    .component('receptList', new ReceptList())
    .service('receptListService', ReceptListService)
    .component('receptDescription', new ReceptDescription())
    .service('receptDescriptionService', ReceptDescriptionService)
    .component('ingridients', new Ingridients())
    .service('ingridientsService', IngridientsService);

export {module, MODULE_NAME as default};
