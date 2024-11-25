<%@ include file="/init.jsp"%>
<portlet:renderURL var="viewFacilitation">
	<portlet:param name="mvcPath"
		value="/facilitationCenter/viewFacilitation.jsp" />
</portlet:renderURL>
<portlet:renderURL var="openFacilitationForm">
	<portlet:param name="mvcPath"
		value="/facilitationCenter/facililtationForm.jsp" />
</portlet:renderURL>
<portlet:renderURL var="facilitationSubscribe">
	<portlet:param name="mvcPath" value="/facilitationCenter/subscribeForm.jsp" />
</portlet:renderURL>
<div class="container mb-4">	 	
	<div class="row">

		<div class="col-md-12">
			<h1>Facilitation Center</h1>

			<p align="justify">This feature 'Facilitation Center' empowers
				retired ONGCians to rent, sell, share goods and services with other
				superannuated colleagues, as this will help them to conveniently
				search for these at one place. It is a one stop solution which has
				these products and services which could be shared between them.</p>

			<p align="justify">Retired ONGCians will be able to offer these
				on Bandhan portal in four categories viz. Matrimonial,
				Accommodation, Car Pooling, House-hold Goods and Vehicles. The user
				has to go to the 'Post Offer' field on the top right and fill up the
				fields and Submit. This will show up in the 'View Offers' page,
				which can be searched under the appropriate Location and Category.</p>


			<a class="btn btn-primary" href="<%=viewFacilitation %>"
				style="text-transform: unset;" target="" title="View Offers">View
				Offers</a> <a class="btn btn-primary" href="<%=openFacilitationForm %>"
				style="text-transform: unset;" target="" title="Post Offers">Post
				Offers </a> <a href="<%= facilitationSubscribe %>" title="Subscribe"
				class="btn btn-primary">Subscribe</a>
		</div>
	</div>

</div>