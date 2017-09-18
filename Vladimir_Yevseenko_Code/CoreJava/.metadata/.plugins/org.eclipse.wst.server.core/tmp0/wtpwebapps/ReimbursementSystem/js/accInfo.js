$(function() {
	$.post('userInfoServlet',
			{},
			function(curUser) {
				if (curUser.isManager) {
					$('#yourReimbursementsBtn').hide();
					$('#newReimbursementBtn').hide();
				} else {
					$('#allReimbursementsBtn').hide();
					$('#registerEmployeeBtn').hide();
					$('#viewEmployeesBtn').hide();
				}
				$('#firstNameIn').attr('placeholder', 'First Name (' + curUser.first + ')');
				$('#lastNameIn').attr('placeholder', 'Last Name (' + curUser.last + ')');
				$('#emailIn').attr('placeholder', 'Email (' + curUser.email + ')');
			});
	
	
	$('#changeUserInfoBtn').click(function() {
			
			$('.errorMsg').each(function() {
				$(this).text('');
			});
			$('#changeInfoMsg').text('');
			
			$('.inField').each(function() {
				$(this).css('border-color', 'rgba(0,0,0,0.3)');
			});
		
			if (!$('#firstNameIn').val() && !$('#lastNameIn').val() 
					&& !$('#emailIn').val() && !$('#passwordIn1').val()) {
				$('#changeInfoMsg').css('color', 'red');
				$('#changeInfoMsg').text('Please fill out at least one field');
				setTimeout(function() {
					$('#changeInfoMsg').text('');
				}, 3000);
				return;
			} else if ($('#emailIn').val().length && !validateEmail($('#emailIn').val())) {
				$('#emailIn').css('border-color', 'red');
				$('#emailMsg').text('Please enter a valid email');
				setTimeout(function() {
					$('#emailIn').css('border-color', 'rgba(0,0,0,0.3)');
					$('#emailMsg').text('');
				}, 3000);
				return;
			} else if ($('#passwordIn1').val() && $('#passwordIn1').val().length < 8) {
				$('#passwordIn1').css('border-color', 'red');
				$('#passwordMsg1').text('Password must be >= 8 characters')
				setTimeout(function() {
					$('#passwordIn1').css('border-color', 'rgba(0,0,0,0.3)');
					$('#passwordMsg1').text('');
				}, 3000);
				return;
			} else if ($('#passwordIn1').val() !== $('#passwordIn2').val()) {
				$('#passwordIn2').css('border-color', 'red');
				$('#passwordMsg2').text('Passwords do not match')
				setTimeout(function() {
					$('#passwordIn2').css('border-color', 'rgba(0,0,0,0.3)');
					$('#passwordMsg2').text('');
				}, 3000);
				return;
			}
		
			$.post('changeUserInfoServlet',
			{ first: $('#firstNameIn').val(), last: $('#lastNameIn').val(),
			  email: $('#emailIn').val(), password: $('#passwordIn1').val() },
			  function(response) {
				  if (response.success) {
					  $('#changeInfoMsg').css('color', 'green');
					  $('#changeInfoMsg').text('Account info updated');
					  $('.inField').each(function() {
							 $(this).val(''); 
					  });
					  setTimeout(function() {
						  $('#changeInfoMsg').text('');
					  }, 3000); 
					  location.reload();
				  } else {
					  $('#emailIn').css('border-color', 'red');
					  $('#emailMsg').text('Email is already in use');
					  setTimeout(function() {
						  $('#emailIn').css('border-color', 'rgba(0,0,0,0.3)');
						  $('#emailMsg').text('');
					  }, 3000);
				  }
			  },
			  'JSON');
	});
	
	$('#logoutBtn').click(function() {
		$.post('logoutServlet',
				{},
				function(data) {
					window.location.replace('login.html');
				});
	});
	
	$('#yourReimbursementsBtn').click(function() {
		window.location.replace('yourReimbursements.html');
	});
	
	$('#allReimbursementsBtn').click(function() {
		window.location.replace('allReimbursements.html')
	});
	
	$('#registerEmployeeBtn').click(function() {
		window.location.replace('registerEmployee.html');
	});
	
	$('#viewEmployeesBtn').click(function() {
		window.location.replace('employees.html');
	});
	
	$('#newReimbursementBtn').click(function() {
		window.location.replace('newReimbursement.html');
	});
});


function validateEmail(email) {
    let re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(email);
}