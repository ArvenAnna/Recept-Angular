import ConvertToNumber from './convert-to-number';
import FileModel from './file-model';
import Autocomplete from './auto-complete';
import ReceptError from './recept-error';

const MODULE_NAME = 'widget';

const module = angular.module(MODULE_NAME, []);

module
    .directive('convertToNumber', ConvertToNumber)
    .directive('fileModel', FileModel)
    .directive('autoComplete', Autocomplete)
    .directive('receptError', ReceptError);

export {module, MODULE_NAME as default};
