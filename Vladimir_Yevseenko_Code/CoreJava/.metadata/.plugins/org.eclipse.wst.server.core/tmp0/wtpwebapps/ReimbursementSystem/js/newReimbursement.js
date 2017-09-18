$(function() {
	$('#submit').click(function() {
		
		$('#amountIn').css('border-color', 'rgba(0,0,0,0.3)');
		$('#amountMsg').text('');
		
		if (!testAmount($('#amountIn').val())) {
			$('#amountIn').css('border-color', 'red');
			$('#amountMsg').text('Please enter a valid amount');
		} else {
			$.post('newReimbursementServlet',
					{ amount: $('#amountIn').val(), 
				      description: $('#descriptionIn').val() ? $('#descriptionIn').val() : "No description" },
					function(response) {
				    	  console.log('newReimbursementServlet success');
				    	  $('#submitMsg').css('color', 'green');
				    	  $('#submitMsg').text('Reimbursement successfuly submitted');
				    	  $('#amountIn').val('');
				    	  $('#descriptionIn').val('');
					});
		}
		
		setTimeout(function() {
			$('#amountIn').css('border-color', 'rgba(0,0,0,0.3)');
			$('#amountMsg').text('');
			$('#submitMsg').text('');
		}, 3000);
	});
	
	
	
	$('#yourReimbursementsBtn').click(function() {
		window.location.replace('yourReimbursements.html');
	});
	
	$('#accInfoBtn').click(function() {
		window.location.replace('accInfo.html');
	});
	
	$('#logoutBtn').click(function() {
		$.post('logoutServlet',
				{},
				function(response) {
					window.location.replace('login.html');
				});
	});
});

function testAmount(amt) {
	var regex = /^\$?[0-9]+(\.[0-9][0-9])?$/;
	return regex.test(amt);
}