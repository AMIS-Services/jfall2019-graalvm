const countries = [{name: 'Afghanistan', code: 'AF'}, 
{name: 'Åland Islands', code: 'AX'}, 
{name: 'Albania', code: 'AL'}, 
{name: 'Algeria', code: 'DZ'}, 
{name: 'American Samoa', code: 'AS'}, 
{name: 'AndorrA', code: 'AD'}, 
{name: 'Angola', code: 'AO'}, 
{name: 'Anguilla', code: 'AI'}, 
{name: 'Antarctica', code: 'AQ'}, 
{name: 'Antigua and Barbuda', code: 'AG'}, 
{name: 'Argentina', code: 'AR'}, 
{name: 'Armenia', code: 'AM'}, 
{name: 'Aruba', code: 'AW'}, 
{name: 'Australia', code: 'AU'}, 
{name: 'Austria', code: 'AT'}, 
{name: 'Azerbaijan', code: 'AZ'}, 
{name: 'Bahamas', code: 'BS'}, 
{name: 'Bahrain', code: 'BH'}, 
{name: 'Bangladesh', code: 'BD'}, 
{name: 'Barbados', code: 'BB'}, 
{name: 'Belarus', code: 'BY'}, 
{name: 'Belgium', code: 'BE'}, 
{name: 'Belize', code: 'BZ'}, 
{name: 'Benin', code: 'BJ'} ]
 // source: https://gist.github.com/keeguon/2310008

const atlas = {worldCountries : countries, message:"hello"}

const answerToLifeUniverseAndEverything = 42;

function countryName(code) {
    for (var i = 0; i < countries.length; i++) {
      if (code == countries[i].code) return countries[i].name
    } //for
    return 'Not found';
 }

 //console.log(countryName('AR'))