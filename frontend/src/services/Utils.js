

function convertUnixTimeStampToString(toConvert){
    // console.log("Time to convert: ",toConvert);
    let newDate = new Date(toConvert).toLocaleString();
    return newDate;
}

//??? bracuhe
//stack overflow 
//https://stackoverflow.com/questions/50432993/native-method-to-inverse-date-tolocalestring-convertion-in-javascript
function createUnixTimeStamp(toConvert){
    if(!!toConvert){
        const [date, time] = toConvert.split(',').map(item => item.trim());
        const [day, month, year] = date.split('.');
        const [hours, minutes, seconds] = time.split(':');
        const newDate = new Date(year, month, day, hours, minutes, seconds);
        return Date.parse(newDate)
    }
}


export { convertUnixTimeStampToString, createUnixTimeStamp };