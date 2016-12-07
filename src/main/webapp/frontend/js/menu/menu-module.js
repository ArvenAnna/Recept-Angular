import Menu from './menu-component';
import MenuService from './menu-service';

const MODULE_NAME = 'menu';

const module = angular.module(MODULE_NAME, []);

module
    .component('receptMenu', new Menu())
    .service('menuService', MenuService);

export {module, MODULE_NAME as default};