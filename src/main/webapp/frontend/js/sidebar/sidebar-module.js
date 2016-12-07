import Sidebar from './sidebar-component';
import SidebarService from './sidebar-service';

const MODULE_NAME = 'sidebar';

const module = angular.module(MODULE_NAME, []);

module
    .component('receptSidebar', new Sidebar())
    .service('sidebarService', SidebarService);

export {module, MODULE_NAME as default};
