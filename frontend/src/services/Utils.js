
/**
 * Format unixtime stamp into readable string
 * @param {Long} toConvert [time in unix format]
 */
function convertUnixTimeStampToString(toConvert){
    // console.log("Time to convert: ",toConvert);
    let newDate = new Date(toConvert).toLocaleString();
    return newDate;
}

export { convertUnixTimeStampToString };