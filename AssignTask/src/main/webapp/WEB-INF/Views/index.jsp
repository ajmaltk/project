<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html lang="en">
<head>
<title>Schedule App</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.15.1/moment.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.7.14/js/bootstrap-datetimepicker.min.js"></script>

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.7.14/css/bootstrap-datetimepicker.min.css" />

<script src="resources/js/Scripts.js" /></script>
<script src="resources/js/Scripts2.js" /></script>
</head>
<body>
	<!-- modal for Save schedule form -->
	<div class="modal fade" id="modalScheduleForm" tabindex="-1"
		role="dialog" aria-labelledby="scheduleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">

				<div class="modal-header text-center">
					<h4 class="modal-title w-100 font-weight-bold">Add Schedule</h4>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>

				<form:form id="schedule" action="/AssignTask/submit"
					modelAttribute="schedule" method="POST">
					<div class="modal-body mx-3">
						<div class="md-form mb-5">
							<i class="fa fa-envelope prefix grey-text"></i>
							<form:label path="scheduleName" class="form-group">Name:<form:input
									class="form-control" id="userName" type="text"
									path="scheduleName" required="true" />
							</form:label>
							<label data-error="wrong" data-success="right" for="userName"></label>
							<div class="md-form mb-4">
								<i class="fa fa-lock prefix grey-text"></i> <label
									class="form-group">Start Time:
									<div class='input-group date' id='datetimepicker1'>
										<form:input type='text' class="form-control" id="startTime"
											path="startTime" data-date-format="HH:mm" required="true" />
										<span class="input-group-addon"> <span
											class="glyphicon glyphicon-calendar"></span>
										</span>

									</div>
								</label>
							</div>
							<div class="md-form mb-4">
								<i class="fa fa-lock prefix grey-text"></i> <label
									class="form-group"> End Time:
									<div class='input-group date' id='datetimepicker2'>
										<form:input type='text' class="form-control" id="endTime"
											path="endTime" data-date-format="HH:mm" required="true" />
										<span class="input-group-addon"> <span
											class="glyphicon glyphicon-calendar"></span>
										</span>
									</div>
								</label>
							</div>

							<form:checkboxes path="weekDaysStatus" items="${weekDaysList}" />
						</div>
					</div>

					<div id="successDiv" class="alert alert-success collapse"
						role="alert">
						<a class="close"></a>
					</div>
					<div id="errorDiv" class="alert alert-danger collapse" role="alert">
						<a class="close"></a>
					</div>
					<div class="modal-footer d-flex justify-content-center">
						<button class="btn btn-primary">Save Schedule</button>
					</div>
				</form:form>
			</div>
		</div>
	</div>


	<!-- modal for Test schedule form -->
	<div class="modal fade" id="modalTestForm" tabindex="-1" role="dialog"
		aria-labelledby="testModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">

				<div class="modal-header text-center">
					<h4 class="modal-title w-100 font-weight-bold">Test Schedule</h4>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>

				<form:form id="task" action="/AssignTask/testTask"
					modelAttribute="task" method="POST">

					<div class="modal-body mx-3">
						<div class="md-form mb-5">
							<i class="fa fa-envelope prefix grey-text"></i>
							<form:label path="taskName" class="form-group">Task Name:
   							<form:input class="form-control" id="taskName" type="text"
									path="taskName" required="true" placeholder="Task Name" />
							</form:label>
							<label data-error="wrong" data-success="right" for="taskName"></label>
							<label class="form-group">Start Date Time:
								<div class='input-group date' id='datetimepicker3'>
									<form:input type='text' class="form-control" id="taskStartTime"
										path="taskStartTime" data-date-format="MM/DD/YYYY HH:mm"
										required="true" />
									<span class="input-group-addon"> <span
										class="glyphicon glyphicon-calendar"></span>
									</span>
								</div>
							</label>
							<form:label path="taskDuration" class="form-group">Duration:<form:input
									class="form-control" id="taskDuration" type="number"
									path="taskDuration" required="true" min="0" />
							</form:label>
						</div>
					</div>
					<div id="errorTaskDiv" class="alert alert-danger collapse"
						role="alert">
						<a class="close"></a>
					</div>
					<div class="modal-footer d-flex justify-content-center">
						<button class="btn btn-primary">Test Schedule</button>
					</div>
				</form:form>
			</div>
		</div>
	</div>

	<!-- modal for result of test -->
	<div class="modal fade" id="testTableModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">Test Schedules</h4>
				</div>
				<div class="modal-body">
					<table class="table table-bordered" id="testTable">
						<thead>
							<th>Start Date Time</th>
							<th>Duration</th>
							<th>End Date Time</th>
						</thead>
						<tbody>
							<tr>
								<td id="idTaskStartTime"></td>
								<td id="idTaskDuration"></td>
								<td id="idTaskEndTime"></td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->

	<!-- button controls  -->
	<div class="container">
		<div class="row" class="col-lg">
			<h2>Schedule</h2>
			<div class="well well-lg">
				<div class="d-flex mb-3">
					<div class="col-xs-4 col-sm-offset-4">
						<a href="" class="btn btn-lg btn-success btn-rounded mb-4"
							data-toggle="modal" data-target="#modalScheduleForm">Add
							Schedule </a>
					</div>
					<div class="col-sm-offset-4">
						<a href="" class="btn btn-lg btn-success btn-rounded mb-9"
							data-toggle="modal" data-target="#modalTestForm">Test
							Schedule </a>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
