var $j = jQuery.noConflict();

$j(document).ready(function() {
	// $('#opciones').hide();
		$j("td").click(tdclick);
	});

function tdclick() {        //¿É±à¼­±í¸ñ
	var td = $j(this);
	var text = td.text();
	td.html("");
	
	

	var trs=td.parent();
	//var tr=$j(this);
	//var trNode=tr.attr("id");
	//var moduleId=trNode.attr("id");
	//alert(trs.attr("id"));
	
	

	var input ;
	if(td.attr("id")!="url")
		input= $j("<input style='width:60px'>");
	else  input= $j("<input style='width:450px'>");
	input.attr("value", text);

	input.keyup(function(event) {
		var myEvent = event || window.event;
		var kcode = myEvent.keyCode;

		if (kcode == 13) {
			var inputnode = $j(this);
			var inputtext = inputnode.val();
			var tdNode = inputnode.parent();

			alert(tdNode.attr("id"));
			
			
			
			tdNode.html(inputtext);
			tdNode.click(tdclick);
		}
	});
	input.blur(function() {
		var inputnode = $j(this);
		var inputtext = inputnode.val();
		var tdNode = inputnode.parent();

		tdNode.html(inputtext);
		tdNode.click(tdclick);
	});

	td.append(input);

	var inputdom = input.get(0);
	inputdom.select();
	td.unbind("click");
}





$j(document).ready(function() {
	$j("#isMobile").click(function() {
	
	alert("dd");
	});


	
	$j("#isMail").click(function() {
	
		if ($("isMail").checked == true) {
			//alert("true");
			$j("#mailinterval").show();
			$j("#mailfrequency").show();
		} else {
			$j("#mailinterval").hide();
			$j("#mailfrequency").hide();
		}
		
	});
});
