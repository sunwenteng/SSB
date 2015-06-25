$(function() {
	$("#logFromTime").datepicker({
		dateFormat : "yymmdd",
		showAnim : "slideDown",
		defaultDate : "+1w",
		changeMonth : true,
		numberOfMonths : 1,
		onClose : function(selectedDate) {
			$("#logToTime").datepicker("option", "minDate", selectedDate);
		}
	});

	$("#logToTime").datepicker({
		dateFormat : "yymmdd",
		showAnim : "slideDown",
		defaultDate : "+1w",
		changeMonth : true,
		numberOfMonths : 1,
		onClose : function(selectedDate) {
			$("#logFromTime").datepicker("option", "maxDate", selectedDate);
		}
	});

	var now = new Date();
	var monthStr = "";
	var dateStr = "";

	if ((now.getMonth() + 1) < 10)
		monthStr = "0" + (now.getMonth() + 1);
	else
		monthStr = now.getMonth() + 1;

	if (now.getDate() < 10)
		dateStr = "0" + now.getDate();
	else
		dateStr = now.getDate();

	var nowStr = now.getFullYear() + "" + monthStr + "" + dateStr;
	$("#logToTime").datepicker("setDate", nowStr);
	$("#logFromTime").datepicker("setDate", nowStr);

});