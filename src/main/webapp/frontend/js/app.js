import $ from 'jquery';
import angular from 'angular';
import 'babel-polyfill';
import MenuModule from './menu/menu-module';
import SidebarModule from './sidebar/sidebar-module';
import ContentModule from './content/content-module';
import WidgetModule from './widget/widget-module';
import Main from './main-component';
import ReceptModel from './recept-model';
import StateService from './state-service';
import CrudService from './crud-service';

/* App Module */

const MODULE_NAME = 'app';

const receptApp = angular.module(MODULE_NAME, [
    WidgetModule,
    MenuModule,
    ContentModule,
    SidebarModule
]);

receptApp.controller('headController', function($scope) {
    $scope.theme = {theme: 'fire'};
});

receptApp.component('main', new Main());

receptApp.service('receptModel', ReceptModel);

receptApp.service('stateService', StateService);

receptApp.service('crudService', CrudService);

receptApp.config(['$compileProvider',
    function ($compileProvider) {
        $compileProvider.aHrefSanitizationWhitelist(/^\s*(https?|ftp|mailto|tel|file|blob):/);
    }]);

//receptApp.run(['stateService', function(stateService) {
//    stateService.init();
//}]);

export {receptApp, MODULE_NAME as default};