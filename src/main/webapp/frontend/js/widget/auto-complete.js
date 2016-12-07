import 'easy-autocomplete';

export default function Autocomplete() {
    return {
        restrict: 'A',
        link: function (scope, element, attrs) {
            $(element).easyAutocomplete({
                url: function (phrase) {
                    return attrs.autoComplete + "?word=" + phrase;
                }
            });
        }
    };
}
