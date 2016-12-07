export default function ReceptError() {
    return {
        restrict: 'A',
        link: function (scope, element, attrs) {
            setTimeout(function () {
                $(element).fadeOut(4000);
            }, 2000);
        }
    };
}