$(document).ready(function()
{
	if ($("#alertSuccess").text().trim() == "")
 	{
 		$("#alertSuccess").hide();
 	}
 	$("#alertError").hide();
 	
});


// SAVE =======================================================================
$(document).on("click", "#btnSave", function(event)
{
	
	// Clear alerts---------------------
 	$("#alertSuccess").text("");
 	$("#alertSuccess").hide();
 	$("#alertError").text("");
 	$("#alertError").hide();
 	
	// Form validation-------------------
	var status = validateUserForm();
	if (status != true)
 	{
	
		$("#alertError").text(status);
		$("#alertError").show();
 		return;
 	}
 	

	// If valid------------------------
	var type = ($("#hidUserSave").val() == "") ? "POST" : "PUT";
	
 	$.ajax(
 	{
 		url : "UserAPI",
 		type : type,
 		data : $("#formUser").serialize(),
 		dataType : "text",
 		complete : function(response, status)
 		{
 			onUserSaveComplete(response.responseText, status);
 		}
 	});
});

function onUserSaveComplete(response, status)
{
	if (status == "success")
 	{
 		var resultSet = JSON.parse(response);
 		if (resultSet.status.trim() == "success")
 		{
	 		$("#alertSuccess").text("Successfully saved.");
	 		$("#alertSuccess").show();
	 		$("#divUsersGrid").html(resultSet.data);
 		
 		} else if (resultSet.status.trim() == "error")
 		{
	 		$("#alertError").text(resultSet.data);
	 		$("#alertError").show();
 		}
 	
 	} else if (status == "error")
 	{
		 $("#alertError").text("Error while saving.");
		 $("#alertError").show();
 	} else
 	{
		 $("#alertError").text("Unknown error while saving..");
		 $("#alertError").show();
 	}
 
	$("#hidUserSave").val("");
	$("#formUser")[0].reset();
}




// UPDATE======================================================================
$(document).on("click", ".btnUpdate", function(event)
{
		//$("#hidUserSave").val($(this).data("Userid"));
		$("#hidUserSave").val($(this).closest("tr").find('#hidUserUpdate').val());
		$("#Username").val($(this).closest("tr").find('td:eq(0)').text());
		$("#Email").val($(this).closest("tr").find('td:eq(1)').text());
		$("#Phoneno").val($(this).closest("tr").find('td:eq(2)').text());
		$("#Nic").val($(this).closest("tr").find('td:eq(3)').text());
		
});




// DELETE======================================================================
$(document).on("click", ".btnRemove", function(event)
{
 		$.ajax(
 		{
			 url : "UserAPI",
			 type : "DELETE",
			 data : "Userid=" + $(this).data("Userid"),
			 dataType : "text",
			 complete : function(response, status)
			 {
			 		onUserDeleteComplete(response.responseText, status);
			 }
 		});
});

function onUserDeleteComplete(response, status)
{
	if (status == "success")
	 {
	 		var resultSet = JSON.parse(response);
	 		
	 		if (resultSet.status.trim() == "success")
	 		{
				 $("#alertSuccess").text("Successfully deleted.");
				 $("#alertSuccess").show();
				 
				 $("#divUsersGrid").html(resultSet.data);
	 		} else if (resultSet.status.trim() == "error")
	 		{
				 $("#alertError").text(resultSet.data);
				 $("#alertError").show();
	 		}
	 		
	 } else if (status == "error")
	 {
			 $("#alertError").text("Error while deleting.");
			 $("#alertError").show();
	 } else
	 {
			 $("#alertError").text("Unknown error while deleting..");
			 $("#alertError").show();
	 }
}





// CLIENT-MODEL================================================================
function validateUserForm()
{
		// name
		if ($("#Username").val().trim() == "")
 		{
			return "Insert user name.";
 		}
 		
		// email
		if ($("#Email").val().trim() == "")
 		{
 			return "Insert email.";
 		}
 		
		// phoneno-------------------------------
		if ($("#Phoneno").val().trim() == "")
 		{
 			return "Insert phoneno.";
 		}
 		
		// NIC------------------------
		if ($("#Nic").val().trim() == "")
 		{
 			return "Insert NIC.";
 		}
		return true;
}

