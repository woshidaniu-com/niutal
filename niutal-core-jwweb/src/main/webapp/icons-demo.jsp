<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.woshidaniu.globalweb.action.IndexAction"%>
<!DOCTYPE html>
<html>

	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/indexhead_v5.ini"%>
		<script type="text/javascript">
			$(document).ready(function() {

				$(".row").find("i").hover(function() {
					//$(this).closest(".row").addClass("ui-state-hover");
				}, function() {
					//$(this).closest(".row").removeClass("ui-state-hover");
				});
				//ui-pg-div ui-inline-del
			});
		</script>
		<style type="text/css">
			.ui-icons [class *="icon-"]:before {
				font-family: FontAwesome;
				display: inline-block;
			}
			
			.ui-icons [class *="icon-"] {
				display: inline-block;
				position: static;
				text-align: center;
				opacity: .85;
				-webkit-transition: all .12s;
				transition: all .12s;
				margin: 0 1px;
				vertical-align: middle;
				cursor: pointer;
				float: left;
				margin-left: 20px;
			}
			
			.ui-icons [class *="icon-得得得"]:hover {
				-moz-transform: scale(1.2);
				-webkit-transform: scale(1.2);
				-o-transform: scale(1.2);
				-ms-transform: scale(1.2);
				transform: scale(1.2);
				opacity: 1;
				position: static;
				margin: 0 1px;
				float: left;
			}
			
			.row i {
				margin-left: 20px;
			}
            .fa{
                font-size: 24px;
                margin: 5px;
            }
		</style>
	</head>

	<body style="padding: 20px">

		
		<div class="icon-container" style="height: 262px;">
			<a class="icon" title="glass" data-icon="fa-glass" data-filter="glass"><i class="fa fa-fw fa-glass fa-sm"></i></a>
			<a class="icon" title="music" data-icon="fa-music" data-filter="music"><i class="fa fa-fw fa-music fa-sm"></i></a>
			<a class="icon" title="search" data-icon="fa-search" data-filter="search"><i class="fa fa-fw fa-search fa-sm"></i></a>
			<a class="icon" title="envelope-o" data-icon="fa-envelope-o" data-filter="envelope-o"><i class="fa fa-fw fa-envelope-o fa-sm"></i></a>
			<a class="icon" title="heart" data-icon="fa-heart" data-filter="heart"><i class="fa fa-fw fa-heart fa-sm"></i></a>
			<a class="icon" title="star" data-icon="fa-star" data-filter="star"><i class="fa fa-fw fa-star fa-sm"></i></a>
			<a class="icon" title="star-o" data-icon="fa-star-o" data-filter="star-o"><i class="fa fa-fw fa-star-o fa-sm"></i></a>
			<a class="icon" title="user" data-icon="fa-user" data-filter="user"><i class="fa fa-fw fa-user fa-sm"></i></a>
			<a class="icon" title="film" data-icon="fa-film" data-filter="film"><i class="fa fa-fw fa-film fa-sm"></i></a>
			<a class="icon" title="th-large" data-icon="fa-th-large" data-filter="th-large"><i class="fa fa-fw fa-th-large fa-sm"></i></a>
			<a class="icon" title="th" data-icon="fa-th" data-filter="th"><i class="fa fa-fw fa-th fa-sm"></i></a>
			<a class="icon" title="th-list" data-icon="fa-th-list" data-filter="th-list"><i class="fa fa-fw fa-th-list fa-sm"></i></a>
			<a class="icon" title="check" data-icon="fa-check" data-filter="check"><i class="fa fa-fw fa-check fa-sm"></i></a>
			<a class="icon" title="times" data-icon="fa-times" data-filter="times"><i class="fa fa-fw fa-times fa-sm"></i></a>
			<a class="icon" title="search-plus" data-icon="fa-search-plus" data-filter="search-plus"><i class="fa fa-fw fa-search-plus fa-sm"></i></a>
			<a class="icon" title="search-minus" data-icon="fa-search-minus" data-filter="search-minus"><i class="fa fa-fw fa-search-minus fa-sm"></i></a>
			<a class="icon" title="power-off" data-icon="fa-power-off" data-filter="power-off"><i class="fa fa-fw fa-power-off fa-sm"></i></a>
			<a class="icon" title="signal" data-icon="fa-signal" data-filter="signal"><i class="fa fa-fw fa-signal fa-sm"></i></a>
			<a class="icon" title="cog" data-icon="fa-cog" data-filter="cog"><i class="fa fa-fw fa-cog fa-sm"></i></a>
			<a class="icon" title="trash-o" data-icon="fa-trash-o" data-filter="trash-o"><i class="fa fa-fw fa-trash-o fa-sm"></i></a>
			<a class="icon" title="home" data-icon="fa-home" data-filter="home"><i class="fa fa-fw fa-home fa-sm"></i></a>
			<a class="icon" title="file-o" data-icon="fa-file-o" data-filter="file-o"><i class="fa fa-fw fa-file-o fa-sm"></i></a>
			<a class="icon" title="clock-o" data-icon="fa-clock-o" data-filter="clock-o"><i class="fa fa-fw fa-clock-o fa-sm"></i></a>
			<a class="icon" title="road" data-icon="fa-road" data-filter="road"><i class="fa fa-fw fa-road fa-sm"></i></a>
			<a class="icon" title="download" data-icon="fa-download" data-filter="download"><i class="fa fa-fw fa-download fa-sm"></i></a>
			<a class="icon" title="arrow-circle-o-down" data-icon="fa-arrow-circle-o-down" data-filter="arrow-circle-o-down"><i class="fa fa-fw fa-arrow-circle-o-down fa-sm"></i></a>
			<a class="icon" title="arrow-circle-o-up" data-icon="fa-arrow-circle-o-up" data-filter="arrow-circle-o-up"><i class="fa fa-fw fa-arrow-circle-o-up fa-sm"></i></a>
			<a class="icon" title="inbox" data-icon="fa-inbox" data-filter="inbox"><i class="fa fa-fw fa-inbox fa-sm"></i></a>
			<a class="icon" title="repeat" data-icon="fa-repeat" data-filter="repeat"><i class="fa fa-fw fa-repeat fa-sm"></i></a>
			<a class="icon" title="refresh" data-icon="fa-refresh" data-filter="refresh"><i class="fa fa-fw fa-refresh fa-sm"></i></a>
			<a class="icon" title="list-alt" data-icon="fa-list-alt" data-filter="list-alt"><i class="fa fa-fw fa-list-alt fa-sm"></i></a>
			<a class="icon" title="lock" data-icon="fa-lock" data-filter="lock"><i class="fa fa-fw fa-lock fa-sm"></i></a>
			<a class="icon" title="flag" data-icon="fa-flag" data-filter="flag"><i class="fa fa-fw fa-flag fa-sm"></i></a>
			<a class="icon" title="headphones" data-icon="fa-headphones" data-filter="headphones"><i class="fa fa-fw fa-headphones fa-sm"></i></a>
			<a class="icon" title="volume-off" data-icon="fa-volume-off" data-filter="volume-off"><i class="fa fa-fw fa-volume-off fa-sm"></i></a>
			<a class="icon" title="volume-down" data-icon="fa-volume-down" data-filter="volume-down"><i class="fa fa-fw fa-volume-down fa-sm"></i></a>
			<a class="icon" title="volume-up" data-icon="fa-volume-up" data-filter="volume-up"><i class="fa fa-fw fa-volume-up fa-sm"></i></a>
			<a class="icon" title="qrcode" data-icon="fa-qrcode" data-filter="qrcode"><i class="fa fa-fw fa-qrcode fa-sm"></i></a>
			<a class="icon" title="barcode" data-icon="fa-barcode" data-filter="barcode"><i class="fa fa-fw fa-barcode fa-sm"></i></a>
			<a class="icon" title="tag" data-icon="fa-tag" data-filter="tag"><i class="fa fa-fw fa-tag fa-sm"></i></a>
			<a class="icon" title="tags" data-icon="fa-tags" data-filter="tags"><i class="fa fa-fw fa-tags fa-sm"></i></a>
			<a class="icon" title="book" data-icon="fa-book" data-filter="book"><i class="fa fa-fw fa-book fa-sm"></i></a>
			<a class="icon" title="bookmark" data-icon="fa-bookmark" data-filter="bookmark"><i class="fa fa-fw fa-bookmark fa-sm"></i></a>
			<a class="icon" title="print" data-icon="fa-print" data-filter="print"><i class="fa fa-fw fa-print fa-sm"></i></a>
			<a class="icon" title="camera" data-icon="fa-camera" data-filter="camera"><i class="fa fa-fw fa-camera fa-sm"></i></a>
			<a class="icon" title="font" data-icon="fa-font" data-filter="font"><i class="fa fa-fw fa-font fa-sm"></i></a>
			<a class="icon" title="bold" data-icon="fa-bold" data-filter="bold"><i class="fa fa-fw fa-bold fa-sm"></i></a>
			<a class="icon" title="italic" data-icon="fa-italic" data-filter="italic"><i class="fa fa-fw fa-italic fa-sm"></i></a>
			<a class="icon" title="text-height" data-icon="fa-text-height" data-filter="text-height"><i class="fa fa-fw fa-text-height fa-sm"></i></a>
			<a class="icon" title="text-width" data-icon="fa-text-width" data-filter="text-width"><i class="fa fa-fw fa-text-width fa-sm"></i></a>
			<a class="icon" title="align-left" data-icon="fa-align-left" data-filter="align-left"><i class="fa fa-fw fa-align-left fa-sm"></i></a>
			<a class="icon" title="align-center" data-icon="fa-align-center" data-filter="align-center"><i class="fa fa-fw fa-align-center fa-sm"></i></a>
			<a class="icon" title="align-right" data-icon="fa-align-right" data-filter="align-right"><i class="fa fa-fw fa-align-right fa-sm"></i></a>
			<a class="icon" title="align-justify" data-icon="fa-align-justify" data-filter="align-justify"><i class="fa fa-fw fa-align-justify fa-sm"></i></a>
			<a class="icon" title="list" data-icon="fa-list" data-filter="list"><i class="fa fa-fw fa-list fa-sm"></i></a>
			<a class="icon" title="outdent" data-icon="fa-outdent" data-filter="outdent"><i class="fa fa-fw fa-outdent fa-sm"></i></a>
			<a class="icon" title="indent" data-icon="fa-indent" data-filter="indent"><i class="fa fa-fw fa-indent fa-sm"></i></a>
			<a class="icon" title="video-camera" data-icon="fa-video-camera" data-filter="video-camera"><i class="fa fa-fw fa-video-camera fa-sm"></i></a>
			<a class="icon" title="picture-o" data-icon="fa-picture-o" data-filter="picture-o"><i class="fa fa-fw fa-picture-o fa-sm"></i></a>
			<a class="icon" title="pencil" data-icon="fa-pencil" data-filter="pencil"><i class="fa fa-fw fa-pencil fa-sm"></i></a>
			<a class="icon" title="map-marker" data-icon="fa-map-marker" data-filter="map-marker"><i class="fa fa-fw fa-map-marker fa-sm"></i></a>
			<a class="icon" title="adjust" data-icon="fa-adjust" data-filter="adjust"><i class="fa fa-fw fa-adjust fa-sm"></i></a>
			<a class="icon" title="tint" data-icon="fa-tint" data-filter="tint"><i class="fa fa-fw fa-tint fa-sm"></i></a>
			<a class="icon" title="pencil-square-o" data-icon="fa-pencil-square-o" data-filter="pencil-square-o"><i class="fa fa-fw fa-pencil-square-o fa-sm"></i></a>
			<a class="icon" title="share-square-o" data-icon="fa-share-square-o" data-filter="share-square-o"><i class="fa fa-fw fa-share-square-o fa-sm"></i></a>
			<a class="icon" title="check-square-o" data-icon="fa-check-square-o" data-filter="check-square-o"><i class="fa fa-fw fa-check-square-o fa-sm"></i></a>
			<a class="icon" title="arrows" data-icon="fa-arrows" data-filter="arrows"><i class="fa fa-fw fa-arrows fa-sm"></i></a>
			<a class="icon" title="step-backward" data-icon="fa-step-backward" data-filter="step-backward"><i class="fa fa-fw fa-step-backward fa-sm"></i></a>
			<a class="icon" title="fast-backward" data-icon="fa-fast-backward" data-filter="fast-backward"><i class="fa fa-fw fa-fast-backward fa-sm"></i></a>
			<a class="icon" title="backward" data-icon="fa-backward" data-filter="backward"><i class="fa fa-fw fa-backward fa-sm"></i></a>
			<a class="icon" title="play" data-icon="fa-play" data-filter="play"><i class="fa fa-fw fa-play fa-sm"></i></a>
			<a class="icon" title="pause" data-icon="fa-pause" data-filter="pause"><i class="fa fa-fw fa-pause fa-sm"></i></a>
			<a class="icon" title="stop" data-icon="fa-stop" data-filter="stop"><i class="fa fa-fw fa-stop fa-sm"></i></a>
			<a class="icon" title="forward" data-icon="fa-forward" data-filter="forward"><i class="fa fa-fw fa-forward fa-sm"></i></a>
			<a class="icon" title="fast-forward" data-icon="fa-fast-forward" data-filter="fast-forward"><i class="fa fa-fw fa-fast-forward fa-sm"></i></a>
			<a class="icon" title="step-forward" data-icon="fa-step-forward" data-filter="step-forward"><i class="fa fa-fw fa-step-forward fa-sm"></i></a>
			<a class="icon" title="eject" data-icon="fa-eject" data-filter="eject"><i class="fa fa-fw fa-eject fa-sm"></i></a>
			<a class="icon" title="chevron-left" data-icon="fa-chevron-left" data-filter="chevron-left"><i class="fa fa-fw fa-chevron-left fa-sm"></i></a>
			<a class="icon" title="chevron-right" data-icon="fa-chevron-right" data-filter="chevron-right"><i class="fa fa-fw fa-chevron-right fa-sm"></i></a>
			<a class="icon" title="plus-circle" data-icon="fa-plus-circle" data-filter="plus-circle"><i class="fa fa-fw fa-plus-circle fa-sm"></i></a>
			<a class="icon" title="minus-circle" data-icon="fa-minus-circle" data-filter="minus-circle"><i class="fa fa-fw fa-minus-circle fa-sm"></i></a>
			<a class="icon" title="times-circle" data-icon="fa-times-circle" data-filter="times-circle"><i class="fa fa-fw fa-times-circle fa-sm"></i></a>
			<a class="icon" title="check-circle" data-icon="fa-check-circle" data-filter="check-circle"><i class="fa fa-fw fa-check-circle fa-sm"></i></a>
			<a class="icon" title="question-circle" data-icon="fa-question-circle" data-filter="question-circle"><i class="fa fa-fw fa-question-circle fa-sm"></i></a>
			<a class="icon" title="info-circle" data-icon="fa-info-circle" data-filter="info-circle"><i class="fa fa-fw fa-info-circle fa-sm"></i></a>
			<a class="icon" title="crosshairs" data-icon="fa-crosshairs" data-filter="crosshairs"><i class="fa fa-fw fa-crosshairs fa-sm"></i></a>
			<a class="icon" title="times-circle-o" data-icon="fa-times-circle-o" data-filter="times-circle-o"><i class="fa fa-fw fa-times-circle-o fa-sm"></i></a>
			<a class="icon" title="check-circle-o" data-icon="fa-check-circle-o" data-filter="check-circle-o"><i class="fa fa-fw fa-check-circle-o fa-sm"></i></a>
			<a class="icon" title="ban" data-icon="fa-ban" data-filter="ban"><i class="fa fa-fw fa-ban fa-sm"></i></a>
			<a class="icon" title="arrow-left" data-icon="fa-arrow-left" data-filter="arrow-left"><i class="fa fa-fw fa-arrow-left fa-sm"></i></a>
			<a class="icon" title="arrow-right" data-icon="fa-arrow-right" data-filter="arrow-right"><i class="fa fa-fw fa-arrow-right fa-sm"></i></a>
			<a class="icon" title="arrow-up" data-icon="fa-arrow-up" data-filter="arrow-up"><i class="fa fa-fw fa-arrow-up fa-sm"></i></a>
			<a class="icon" title="arrow-down" data-icon="fa-arrow-down" data-filter="arrow-down"><i class="fa fa-fw fa-arrow-down fa-sm"></i></a>
			<a class="icon" title="share" data-icon="fa-share" data-filter="share"><i class="fa fa-fw fa-share fa-sm"></i></a>
			<a class="icon" title="expand" data-icon="fa-expand" data-filter="expand"><i class="fa fa-fw fa-expand fa-sm"></i></a>
			<a class="icon" title="compress" data-icon="fa-compress" data-filter="compress"><i class="fa fa-fw fa-compress fa-sm"></i></a>
			<a class="icon" title="plus" data-icon="fa-plus" data-filter="plus"><i class="fa fa-fw fa-plus fa-sm"></i></a>
			<a class="icon" title="minus" data-icon="fa-minus" data-filter="minus"><i class="fa fa-fw fa-minus fa-sm"></i></a>
			<a class="icon" title="asterisk" data-icon="fa-asterisk" data-filter="asterisk"><i class="fa fa-fw fa-asterisk fa-sm"></i></a>
			<a class="icon" title="exclamation-circle" data-icon="fa-exclamation-circle" data-filter="exclamation-circle"><i class="fa fa-fw fa-exclamation-circle fa-sm"></i></a>
			<a class="icon" title="gift" data-icon="fa-gift" data-filter="gift"><i class="fa fa-fw fa-gift fa-sm"></i></a>
			<a class="icon" title="leaf" data-icon="fa-leaf" data-filter="leaf"><i class="fa fa-fw fa-leaf fa-sm"></i></a>
			<a class="icon" title="fire" data-icon="fa-fire" data-filter="fire"><i class="fa fa-fw fa-fire fa-sm"></i></a>
			<a class="icon" title="eye" data-icon="fa-eye" data-filter="eye"><i class="fa fa-fw fa-eye fa-sm"></i></a>
			<a class="icon" title="eye-slash" data-icon="fa-eye-slash" data-filter="eye-slash"><i class="fa fa-fw fa-eye-slash fa-sm"></i></a>
			<a class="icon" title="exclamation-triangle" data-icon="fa-exclamation-triangle" data-filter="exclamation-triangle"><i class="fa fa-fw fa-exclamation-triangle fa-sm"></i></a>
			<a class="icon" title="plane" data-icon="fa-plane" data-filter="plane"><i class="fa fa-fw fa-plane fa-sm"></i></a>
			<a class="icon" title="calendar" data-icon="fa-calendar" data-filter="calendar"><i class="fa fa-fw fa-calendar fa-sm"></i></a>
			<a class="icon" title="random" data-icon="fa-random" data-filter="random"><i class="fa fa-fw fa-random fa-sm"></i></a>
			<a class="icon" title="comment" data-icon="fa-comment" data-filter="comment"><i class="fa fa-fw fa-comment fa-sm"></i></a>
			<a class="icon" title="magnet" data-icon="fa-magnet" data-filter="magnet"><i class="fa fa-fw fa-magnet fa-sm"></i></a>
			<a class="icon" title="chevron-up" data-icon="fa-chevron-up" data-filter="chevron-up"><i class="fa fa-fw fa-chevron-up fa-sm"></i></a>
			<a class="icon" title="chevron-down" data-icon="fa-chevron-down" data-filter="chevron-down"><i class="fa fa-fw fa-chevron-down fa-sm"></i></a>
			<a class="icon" title="retweet" data-icon="fa-retweet" data-filter="retweet"><i class="fa fa-fw fa-retweet fa-sm"></i></a>
			<a class="icon" title="shopping-cart" data-icon="fa-shopping-cart" data-filter="shopping-cart"><i class="fa fa-fw fa-shopping-cart fa-sm"></i></a>
			<a class="icon" title="folder" data-icon="fa-folder" data-filter="folder"><i class="fa fa-fw fa-folder fa-sm"></i></a>
			<a class="icon" title="folder-open" data-icon="fa-folder-open" data-filter="folder-open"><i class="fa fa-fw fa-folder-open fa-sm"></i></a>
			<a class="icon" title="arrows-v" data-icon="fa-arrows-v" data-filter="arrows-v"><i class="fa fa-fw fa-arrows-v fa-sm"></i></a>
			<a class="icon" title="arrows-h" data-icon="fa-arrows-h" data-filter="arrows-h"><i class="fa fa-fw fa-arrows-h fa-sm"></i></a>
			<a class="icon" title="bar-chart-o" data-icon="fa-bar-chart-o" data-filter="bar-chart-o"><i class="fa fa-fw fa-bar-chart-o fa-sm"></i></a>
			<a class="icon" title="twitter-square" data-icon="fa-twitter-square" data-filter="twitter-square"><i class="fa fa-fw fa-twitter-square fa-sm"></i></a>
			<a class="icon" title="facebook-square" data-icon="fa-facebook-square" data-filter="facebook-square"><i class="fa fa-fw fa-facebook-square fa-sm"></i></a>
			<a class="icon" title="camera-retro" data-icon="fa-camera-retro" data-filter="camera-retro"><i class="fa fa-fw fa-camera-retro fa-sm"></i></a>
			<a class="icon" title="cogs" data-icon="fa-cogs" data-filter="cogs"><i class="fa fa-fw fa-cogs fa-sm"></i></a>
			<a class="icon" title="comments" data-icon="fa-comments" data-filter="comments"><i class="fa fa-fw fa-comments fa-sm"></i></a>
			<a class="icon" title="thumbs-o-down" data-icon="fa-thumbs-o-down" data-filter="thumbs-o-down"><i class="fa fa-fw fa-thumbs-o-down fa-sm"></i></a>
			<a class="icon" title="heart-o" data-icon="fa-heart-o" data-filter="heart-o"><i class="fa fa-fw fa-heart-o fa-sm"></i></a>
			<a class="icon" title="linkedin-square" data-icon="fa-linkedin-square" data-filter="linkedin-square"><i class="fa fa-fw fa-linkedin-square fa-sm"></i></a>
			<a class="icon" title="external-link" data-icon="fa-external-link" data-filter="external-link"><i class="fa fa-fw fa-external-link fa-sm"></i></a>
			<a class="icon" title="thumb-tack" data-icon="fa-thumb-tack" data-filter="thumb-tack"><i class="fa fa-fw fa-thumb-tack fa-sm"></i></a>
			<a class="icon" title="sign-in" data-icon="fa-sign-in" data-filter="sign-in"><i class="fa fa-fw fa-sign-in fa-sm"></i></a>
			<a class="icon" title="trophy" data-icon="fa-trophy" data-filter="trophy"><i class="fa fa-fw fa-trophy fa-sm"></i></a>
			<a class="icon" title="github-square" data-icon="fa-github-square" data-filter="github-square"><i class="fa fa-fw fa-github-square fa-sm"></i></a>
			<a class="icon" title="upload" data-icon="fa-upload" data-filter="upload"><i class="fa fa-fw fa-upload fa-sm"></i></a>
			<a class="icon" title="lemon-o" data-icon="fa-lemon-o" data-filter="lemon-o"><i class="fa fa-fw fa-lemon-o fa-sm"></i></a>
			<a class="icon" title="phone" data-icon="fa-phone" data-filter="phone"><i class="fa fa-fw fa-phone fa-sm"></i></a>
			<a class="icon" title="square-o" data-icon="fa-square-o" data-filter="square-o"><i class="fa fa-fw fa-square-o fa-sm"></i></a>
			<a class="icon" title="bookmark-o" data-icon="fa-bookmark-o" data-filter="bookmark-o"><i class="fa fa-fw fa-bookmark-o fa-sm"></i></a>
			<a class="icon" title="phone-square" data-icon="fa-phone-square" data-filter="phone-square"><i class="fa fa-fw fa-phone-square fa-sm"></i></a>
			<a class="icon" title="twitter" data-icon="fa-twitter" data-filter="twitter"><i class="fa fa-fw fa-twitter fa-sm"></i></a>
			<a class="icon" title="facebook" data-icon="fa-facebook" data-filter="facebook"><i class="fa fa-fw fa-facebook fa-sm"></i></a>
			<a class="icon" title="github" data-icon="fa-github" data-filter="github"><i class="fa fa-fw fa-github fa-sm"></i></a>
			<a class="icon" title="unlock" data-icon="fa-unlock" data-filter="unlock"><i class="fa fa-fw fa-unlock fa-sm"></i></a>
			<a class="icon" title="credit-card" data-icon="fa-credit-card" data-filter="credit-card"><i class="fa fa-fw fa-credit-card fa-sm"></i></a>
			<a class="icon" title="rss" data-icon="fa-rss" data-filter="rss"><i class="fa fa-fw fa-rss fa-sm"></i></a>
			<a class="icon" title="hdd-o" data-icon="fa-hdd-o" data-filter="hdd-o"><i class="fa fa-fw fa-hdd-o fa-sm"></i></a>
			<a class="icon" title="bullhorn" data-icon="fa-bullhorn" data-filter="bullhorn"><i class="fa fa-fw fa-bullhorn fa-sm"></i></a>
			<a class="icon" title="bell" data-icon="fa-bell" data-filter="bell"><i class="fa fa-fw fa-bell fa-sm"></i></a>
			<a class="icon" title="certificate" data-icon="fa-certificate" data-filter="certificate"><i class="fa fa-fw fa-certificate fa-sm"></i></a>
			<a class="icon" title="hand-o-right" data-icon="fa-hand-o-right" data-filter="hand-o-right"><i class="fa fa-fw fa-hand-o-right fa-sm"></i></a>
			<a class="icon" title="hand-o-left" data-icon="fa-hand-o-left" data-filter="hand-o-left"><i class="fa fa-fw fa-hand-o-left fa-sm"></i></a>
			<a class="icon" title="hand-o-up" data-icon="fa-hand-o-up" data-filter="hand-o-up"><i class="fa fa-fw fa-hand-o-up fa-sm"></i></a>
			<a class="icon" title="hand-o-down" data-icon="fa-hand-o-down" data-filter="hand-o-down"><i class="fa fa-fw fa-hand-o-down fa-sm"></i></a>
			<a class="icon" title="arrow-circle-left" data-icon="fa-arrow-circle-left" data-filter="arrow-circle-left"><i class="fa fa-fw fa-arrow-circle-left fa-sm"></i></a>
			<a class="icon" title="arrow-circle-right" data-icon="fa-arrow-circle-right" data-filter="arrow-circle-right"><i class="fa fa-fw fa-arrow-circle-right fa-sm"></i></a>
			<a class="icon" title="arrow-circle-up" data-icon="fa-arrow-circle-up" data-filter="arrow-circle-up"><i class="fa fa-fw fa-arrow-circle-up fa-sm"></i></a>
			<a class="icon" title="arrow-circle-down" data-icon="fa-arrow-circle-down" data-filter="arrow-circle-down"><i class="fa fa-fw fa-arrow-circle-down fa-sm"></i></a>
			<a class="icon" title="globe" data-icon="fa-globe" data-filter="globe"><i class="fa fa-fw fa-globe fa-sm"></i></a>
			<a class="icon" title="wrench" data-icon="fa-wrench" data-filter="wrench"><i class="fa fa-fw fa-wrench fa-sm"></i></a>
			<a class="icon" title="tasks" data-icon="fa-tasks" data-filter="tasks"><i class="fa fa-fw fa-tasks fa-sm"></i></a>
			<a class="icon" title="filter" data-icon="fa-filter" data-filter="filter"><i class="fa fa-fw fa-filter fa-sm"></i></a>
			<a class="icon" title="briefcase" data-icon="fa-briefcase" data-filter="briefcase"><i class="fa fa-fw fa-briefcase fa-sm"></i></a>
			<a class="icon" title="arrows-alt" data-icon="fa-arrows-alt" data-filter="arrows-alt"><i class="fa fa-fw fa-arrows-alt fa-sm"></i></a>
			<a class="icon" title="users" data-icon="fa-users" data-filter="users"><i class="fa fa-fw fa-users fa-sm"></i></a>
			<a class="icon" title="link" data-icon="fa-link" data-filter="link"><i class="fa fa-fw fa-link fa-sm"></i></a>
			<a class="icon" title="cloud" data-icon="fa-cloud" data-filter="cloud"><i class="fa fa-fw fa-cloud fa-sm"></i></a>
			<a class="icon" title="flask" data-icon="fa-flask" data-filter="flask"><i class="fa fa-fw fa-flask fa-sm"></i></a>
			<a class="icon" title="scissors" data-icon="fa-scissors" data-filter="scissors"><i class="fa fa-fw fa-scissors fa-sm"></i></a>
			<a class="icon" title="files-o" data-icon="fa-files-o" data-filter="files-o"><i class="fa fa-fw fa-files-o fa-sm"></i></a>
			<a class="icon" title="paperclip" data-icon="fa-paperclip" data-filter="paperclip"><i class="fa fa-fw fa-paperclip fa-sm"></i></a>
			<a class="icon" title="floppy-o" data-icon="fa-floppy-o" data-filter="floppy-o"><i class="fa fa-fw fa-floppy-o fa-sm"></i></a>
			<a class="icon" title="square" data-icon="fa-square" data-filter="square"><i class="fa fa-fw fa-square fa-sm"></i></a>
			<a class="icon" title="bars" data-icon="fa-bars" data-filter="bars"><i class="fa fa-fw fa-bars fa-sm"></i></a>
			<a class="icon" title="list-ul" data-icon="fa-list-ul" data-filter="list-ul"><i class="fa fa-fw fa-list-ul fa-sm"></i></a>
			<a class="icon" title="list-ol" data-icon="fa-list-ol" data-filter="list-ol"><i class="fa fa-fw fa-list-ol fa-sm"></i></a>
			<a class="icon" title="strikethrough" data-icon="fa-strikethrough" data-filter="strikethrough"><i class="fa fa-fw fa-strikethrough fa-sm"></i></a>
			<a class="icon" title="underline" data-icon="fa-underline" data-filter="underline"><i class="fa fa-fw fa-underline fa-sm"></i></a>
			<a class="icon" title="table" data-icon="fa-table" data-filter="table"><i class="fa fa-fw fa-table fa-sm"></i></a>
			<a class="icon" title="magic" data-icon="fa-magic" data-filter="magic"><i class="fa fa-fw fa-magic fa-sm"></i></a>
			<a class="icon" title="truck" data-icon="fa-truck" data-filter="truck"><i class="fa fa-fw fa-truck fa-sm"></i></a>
			<a class="icon" title="pinterest" data-icon="fa-pinterest" data-filter="pinterest"><i class="fa fa-fw fa-pinterest fa-sm"></i></a>
			<a class="icon" title="pinterest-square" data-icon="fa-pinterest-square" data-filter="pinterest-square"><i class="fa fa-fw fa-pinterest-square fa-sm"></i></a>
			<a class="icon" title="google-plus-square" data-icon="fa-google-plus-square" data-filter="google-plus-square"><i class="fa fa-fw fa-google-plus-square fa-sm"></i></a>
			<a class="icon" title="google-plus" data-icon="fa-google-plus" data-filter="google-plus"><i class="fa fa-fw fa-google-plus fa-sm"></i></a>
			<a class="icon" title="money" data-icon="fa-money" data-filter="money"><i class="fa fa-fw fa-money fa-sm"></i></a>
			<a class="icon" title="caret-down" data-icon="fa-caret-down" data-filter="caret-down"><i class="fa fa-fw fa-caret-down fa-sm"></i></a>
			<a class="icon" title="caret-up" data-icon="fa-caret-up" data-filter="caret-up"><i class="fa fa-fw fa-caret-up fa-sm"></i></a>
			<a class="icon" title="caret-left" data-icon="fa-caret-left" data-filter="caret-left"><i class="fa fa-fw fa-caret-left fa-sm"></i></a>
			<a class="icon" title="caret-right" data-icon="fa-caret-right" data-filter="caret-right"><i class="fa fa-fw fa-caret-right fa-sm"></i></a>
			<a class="icon" title="columns" data-icon="fa-columns" data-filter="columns"><i class="fa fa-fw fa-columns fa-sm"></i></a>
			<a class="icon" title="sort" data-icon="fa-sort" data-filter="sort"><i class="fa fa-fw fa-sort fa-sm"></i></a>
			<a class="icon" title="sort-asc" data-icon="fa-sort-asc" data-filter="sort-asc"><i class="fa fa-fw fa-sort-asc fa-sm"></i></a>
			<a class="icon" title="sort-desc" data-icon="fa-sort-desc" data-filter="sort-desc"><i class="fa fa-fw fa-sort-desc fa-sm"></i></a>
			<a class="icon" title="envelope" data-icon="fa-envelope" data-filter="envelope"><i class="fa fa-fw fa-envelope fa-sm"></i></a>
			<a class="icon" title="linkedin" data-icon="fa-linkedin" data-filter="linkedin"><i class="fa fa-fw fa-linkedin fa-sm"></i></a>
			<a class="icon" title="undo" data-icon="fa-undo" data-filter="undo"><i class="fa fa-fw fa-undo fa-sm"></i></a>
			<a class="icon" title="gavel" data-icon="fa-gavel" data-filter="gavel"><i class="fa fa-fw fa-gavel fa-sm"></i></a>
			<a class="icon" title="tachometer" data-icon="fa-tachometer" data-filter="tachometer"><i class="fa fa-fw fa-tachometer fa-sm"></i></a>
			<a class="icon" title="comment-o" data-icon="fa-comment-o" data-filter="comment-o"><i class="fa fa-fw fa-comment-o fa-sm"></i></a>
			<a class="icon" title="comments-o" data-icon="fa-comments-o" data-filter="comments-o"><i class="fa fa-fw fa-comments-o fa-sm"></i></a>
			<a class="icon" title="bolt" data-icon="fa-bolt" data-filter="bolt"><i class="fa fa-fw fa-bolt fa-sm"></i></a>
			<a class="icon" title="sitemap" data-icon="fa-sitemap" data-filter="sitemap"><i class="fa fa-fw fa-sitemap fa-sm"></i></a>
			<a class="icon" title="umbrella" data-icon="fa-umbrella" data-filter="umbrella"><i class="fa fa-fw fa-umbrella fa-sm"></i></a>
			<a class="icon" title="clipboard" data-icon="fa-clipboard" data-filter="clipboard"><i class="fa fa-fw fa-clipboard fa-sm"></i></a>
			<a class="icon" title="lightbulb-o" data-icon="fa-lightbulb-o" data-filter="lightbulb-o"><i class="fa fa-fw fa-lightbulb-o fa-sm"></i></a>
			<a class="icon" title="exchange" data-icon="fa-exchange" data-filter="exchange"><i class="fa fa-fw fa-exchange fa-sm"></i></a>
			<a class="icon" title="cloud-download" data-icon="fa-cloud-download" data-filter="cloud-download"><i class="fa fa-fw fa-cloud-download fa-sm"></i></a>
			<a class="icon" title="cloud-upload" data-icon="fa-cloud-upload" data-filter="cloud-upload"><i class="fa fa-fw fa-cloud-upload fa-sm"></i></a>
			<a class="icon" title="user-md" data-icon="fa-user-md" data-filter="user-md"><i class="fa fa-fw fa-user-md fa-sm"></i></a>
			<a class="icon" title="stethoscope" data-icon="fa-stethoscope" data-filter="stethoscope"><i class="fa fa-fw fa-stethoscope fa-sm"></i></a>
			<a class="icon" title="suitcase" data-icon="fa-suitcase" data-filter="suitcase"><i class="fa fa-fw fa-suitcase fa-sm"></i></a>
			<a class="icon" title="bell-o" data-icon="fa-bell-o" data-filter="bell-o"><i class="fa fa-fw fa-bell-o fa-sm"></i></a>
			<a class="icon" title="coffee" data-icon="fa-coffee" data-filter="coffee"><i class="fa fa-fw fa-coffee fa-sm"></i></a>
			<a class="icon" title="cutlery" data-icon="fa-cutlery" data-filter="cutlery"><i class="fa fa-fw fa-cutlery fa-sm"></i></a>
			<a class="icon" title="file-text-o" data-icon="fa-file-text-o" data-filter="file-text-o"><i class="fa fa-fw fa-file-text-o fa-sm"></i></a>
			<a class="icon" title="building-o" data-icon="fa-building-o" data-filter="building-o"><i class="fa fa-fw fa-building-o fa-sm"></i></a>
			<a class="icon" title="hospital-o" data-icon="fa-hospital-o" data-filter="hospital-o"><i class="fa fa-fw fa-hospital-o fa-sm"></i></a>
			<a class="icon" title="ambulance" data-icon="fa-ambulance" data-filter="ambulance"><i class="fa fa-fw fa-ambulance fa-sm"></i></a>
			<a class="icon" title="medkit" data-icon="fa-medkit" data-filter="medkit"><i class="fa fa-fw fa-medkit fa-sm"></i></a>
			<a class="icon" title="fighter-jet" data-icon="fa-fighter-jet" data-filter="fighter-jet"><i class="fa fa-fw fa-fighter-jet fa-sm"></i></a>
			<a class="icon" title="beer" data-icon="fa-beer" data-filter="beer"><i class="fa fa-fw fa-beer fa-sm"></i></a>
			<a class="icon" title="h-square" data-icon="fa-h-square" data-filter="h-square"><i class="fa fa-fw fa-h-square fa-sm"></i></a>
			<a class="icon" title="plus-square" data-icon="fa-plus-square" data-filter="plus-square"><i class="fa fa-fw fa-plus-square fa-sm"></i></a>
			<a class="icon" title="angle-double-left" data-icon="fa-angle-double-left" data-filter="angle-double-left"><i class="fa fa-fw fa-angle-double-left fa-sm"></i></a>
			<a class="icon" title="angle-double-right" data-icon="fa-angle-double-right" data-filter="angle-double-right"><i class="fa fa-fw fa-angle-double-right fa-sm"></i></a>
			<a class="icon" title="angle-double-up" data-icon="fa-angle-double-up" data-filter="angle-double-up"><i class="fa fa-fw fa-angle-double-up fa-sm"></i></a>
			<a class="icon" title="angle-double-down" data-icon="fa-angle-double-down" data-filter="angle-double-down"><i class="fa fa-fw fa-angle-double-down fa-sm"></i></a>
			<a class="icon" title="angle-left" data-icon="fa-angle-left" data-filter="angle-left"><i class="fa fa-fw fa-angle-left fa-sm"></i></a>
			<a class="icon" title="angle-up" data-icon="fa-angle-up" data-filter="angle-up"><i class="fa fa-fw fa-angle-up fa-sm"></i></a>
			<a class="icon" title="angle-down" data-icon="fa-angle-down" data-filter="angle-down"><i class="fa fa-fw fa-angle-down fa-sm"></i></a>
			<a class="icon" title="desktop" data-icon="fa-desktop" data-filter="desktop"><i class="fa fa-fw fa-desktop fa-sm"></i></a>
			<a class="icon" title="laptop" data-icon="fa-laptop" data-filter="laptop"><i class="fa fa-fw fa-laptop fa-sm"></i></a>
			<a class="icon" title="tablet" data-icon="fa-tablet" data-filter="tablet"><i class="fa fa-fw fa-tablet fa-sm"></i></a>
			<a class="icon" title="mobile" data-icon="fa-mobile" data-filter="mobile"><i class="fa fa-fw fa-mobile fa-sm"></i></a>
			<a class="icon" title="circle-o" data-icon="fa-circle-o" data-filter="circle-o"><i class="fa fa-fw fa-circle-o fa-sm"></i></a>
			<a class="icon" title="quote-left" data-icon="fa-quote-left" data-filter="quote-left"><i class="fa fa-fw fa-quote-left fa-sm"></i></a>
			<a class="icon" title="quote-right" data-icon="fa-quote-right" data-filter="quote-right"><i class="fa fa-fw fa-quote-right fa-sm"></i></a>
			<a class="icon" title="spinner" data-icon="fa-spinner" data-filter="spinner"><i class="fa fa-fw fa-spinner fa-sm"></i></a>
			<a class="icon" title="circle" data-icon="fa-circle" data-filter="circle"><i class="fa fa-fw fa-circle fa-sm"></i></a>
			<a class="icon" title="reply" data-icon="fa-reply" data-filter="reply"><i class="fa fa-fw fa-reply fa-sm"></i></a>
			<a class="icon" title="github-alt" data-icon="fa-github-alt" data-filter="github-alt"><i class="fa fa-fw fa-github-alt fa-sm"></i></a>
			<a class="icon" title="folder-o" data-icon="fa-folder-o" data-filter="folder-o"><i class="fa fa-fw fa-folder-o fa-sm"></i></a>
			<a class="icon" title="folder-open-o" data-icon="fa-folder-open-o" data-filter="folder-open-o"><i class="fa fa-fw fa-folder-open-o fa-sm"></i></a>
			<a class="icon" title="smile-o" data-icon="fa-smile-o" data-filter="smile-o"><i class="fa fa-fw fa-smile-o fa-sm"></i></a>
			<a class="icon" title="frown-o" data-icon="fa-frown-o" data-filter="frown-o"><i class="fa fa-fw fa-frown-o fa-sm"></i></a>
			<a class="icon" title="meh-o" data-icon="fa-meh-o" data-filter="meh-o"><i class="fa fa-fw fa-meh-o fa-sm"></i></a>
			<a class="icon" title="gamepad" data-icon="fa-gamepad" data-filter="gamepad"><i class="fa fa-fw fa-gamepad fa-sm"></i></a>
			<a class="icon" title="keyboard-o" data-icon="fa-keyboard-o" data-filter="keyboard-o"><i class="fa fa-fw fa-keyboard-o fa-sm"></i></a>
			<a class="icon" title="flag-o" data-icon="fa-flag-o" data-filter="flag-o"><i class="fa fa-fw fa-flag-o fa-sm"></i></a>
			<a class="icon" title="flag-checkered" data-icon="fa-flag-checkered" data-filter="flag-checkered"><i class="fa fa-fw fa-flag-checkered fa-sm"></i></a>
			<a class="icon" title="terminal" data-icon="fa-terminal" data-filter="terminal"><i class="fa fa-fw fa-terminal fa-sm"></i></a>
			<a class="icon" title="code" data-icon="fa-code" data-filter="code"><i class="fa fa-fw fa-code fa-sm"></i></a>
			<a class="icon" title="reply-all" data-icon="fa-reply-all" data-filter="reply-all"><i class="fa fa-fw fa-reply-all fa-sm"></i></a>
			<a class="icon" title="mail-reply-all" data-icon="fa-mail-reply-all" data-filter="mail-reply-all"><i class="fa fa-fw fa-mail-reply-all fa-sm"></i></a>
			<a class="icon" title="star-half-o" data-icon="fa-star-half-o" data-filter="star-half-o"><i class="fa fa-fw fa-star-half-o fa-sm"></i></a>
			<a class="icon" title="location-arrow" data-icon="fa-location-arrow" data-filter="location-arrow"><i class="fa fa-fw fa-location-arrow fa-sm"></i></a>
			<a class="icon" title="crop" data-icon="fa-crop" data-filter="crop"><i class="fa fa-fw fa-crop fa-sm"></i></a>
			<a class="icon" title="code-fork" data-icon="fa-code-fork" data-filter="code-fork"><i class="fa fa-fw fa-code-fork fa-sm"></i></a>
			<a class="icon" title="chain-broken" data-icon="fa-chain-broken" data-filter="chain-broken"><i class="fa fa-fw fa-chain-broken fa-sm"></i></a>
			<a class="icon" title="question" data-icon="fa-question" data-filter="question"><i class="fa fa-fw fa-question fa-sm"></i></a>
			<a class="icon" title="info" data-icon="fa-info" data-filter="info"><i class="fa fa-fw fa-info fa-sm"></i></a>
			<a class="icon" title="exclamation" data-icon="fa-exclamation" data-filter="exclamation"><i class="fa fa-fw fa-exclamation fa-sm"></i></a>
			<a class="icon" title="superscript" data-icon="fa-superscript" data-filter="superscript"><i class="fa fa-fw fa-superscript fa-sm"></i></a>
			<a class="icon" title="subscript" data-icon="fa-subscript" data-filter="subscript"><i class="fa fa-fw fa-subscript fa-sm"></i></a>
			<a class="icon" title="eraser" data-icon="fa-eraser" data-filter="eraser"><i class="fa fa-fw fa-eraser fa-sm"></i></a>
			<a class="icon" title="puzzle-piece" data-icon="fa-puzzle-piece" data-filter="puzzle-piece"><i class="fa fa-fw fa-puzzle-piece fa-sm"></i></a>
			<a class="icon" title="microphone" data-icon="fa-microphone" data-filter="microphone"><i class="fa fa-fw fa-microphone fa-sm"></i></a>
			<a class="icon" title="microphone-slash" data-icon="fa-microphone-slash" data-filter="microphone-slash"><i class="fa fa-fw fa-microphone-slash fa-sm"></i></a>
			<a class="icon" title="shield" data-icon="fa-shield" data-filter="shield"><i class="fa fa-fw fa-shield fa-sm"></i></a>
			<a class="icon" title="calendar-o" data-icon="fa-calendar-o" data-filter="calendar-o"><i class="fa fa-fw fa-calendar-o fa-sm"></i></a>
			<a class="icon" title="fire-extinguisher" data-icon="fa-fire-extinguisher" data-filter="fire-extinguisher"><i class="fa fa-fw fa-fire-extinguisher fa-sm"></i></a>
			<a class="icon" title="rocket" data-icon="fa-rocket" data-filter="rocket"><i class="fa fa-fw fa-rocket fa-sm"></i></a>
			<a class="icon" title="maxcdn" data-icon="fa-maxcdn" data-filter="maxcdn"><i class="fa fa-fw fa-maxcdn fa-sm"></i></a>
			<a class="icon" title="chevron-circle-left" data-icon="fa-chevron-circle-left" data-filter="chevron-circle-left"><i class="fa fa-fw fa-chevron-circle-left fa-sm"></i></a>
			<a class="icon" title="chevron-circle-right" data-icon="fa-chevron-circle-right" data-filter="chevron-circle-right"><i class="fa fa-fw fa-chevron-circle-right fa-sm"></i></a>
			<a class="icon" title="chevron-circle-up" data-icon="fa-chevron-circle-up" data-filter="chevron-circle-up"><i class="fa fa-fw fa-chevron-circle-up fa-sm"></i></a>
			<a class="icon" title="chevron-circle-down" data-icon="fa-chevron-circle-down" data-filter="chevron-circle-down"><i class="fa fa-fw fa-chevron-circle-down fa-sm"></i></a>
			<a class="icon" title="html5" data-icon="fa-html5" data-filter="html5"><i class="fa fa-fw fa-html5 fa-sm"></i></a>
			<a class="icon" title="css3" data-icon="fa-css3" data-filter="css3"><i class="fa fa-fw fa-css3 fa-sm"></i></a>
			<a class="icon" title="anchor" data-icon="fa-anchor" data-filter="anchor"><i class="fa fa-fw fa-anchor fa-sm"></i></a>
			<a class="icon" title="unlock-alt" data-icon="fa-unlock-alt" data-filter="unlock-alt"><i class="fa fa-fw fa-unlock-alt fa-sm"></i></a>
			<a class="icon" title="bullseye" data-icon="fa-bullseye" data-filter="bullseye"><i class="fa fa-fw fa-bullseye fa-sm"></i></a>
			<a class="icon" title="ellipsis-h" data-icon="fa-ellipsis-h" data-filter="ellipsis-h"><i class="fa fa-fw fa-ellipsis-h fa-sm"></i></a>
			<a class="icon" title="ellipsis-v" data-icon="fa-ellipsis-v" data-filter="ellipsis-v"><i class="fa fa-fw fa-ellipsis-v fa-sm"></i></a>
			<a class="icon" title="rss-square" data-icon="fa-rss-square" data-filter="rss-square"><i class="fa fa-fw fa-rss-square fa-sm"></i></a>
			<a class="icon" title="play-circle" data-icon="fa-play-circle" data-filter="play-circle"><i class="fa fa-fw fa-play-circle fa-sm"></i></a>
			<a class="icon" title="ticket" data-icon="fa-ticket" data-filter="ticket"><i class="fa fa-fw fa-ticket fa-sm"></i></a>
			<a class="icon" title="minus-square" data-icon="fa-minus-square" data-filter="minus-square"><i class="fa fa-fw fa-minus-square fa-sm"></i></a>
			<a class="icon" title="minus-square-o" data-icon="fa-minus-square-o" data-filter="minus-square-o"><i class="fa fa-fw fa-minus-square-o fa-sm"></i></a>
			<a class="icon" title="level-up" data-icon="fa-level-up" data-filter="level-up"><i class="fa fa-fw fa-level-up fa-sm"></i></a>
			<a class="icon" title="level-down" data-icon="fa-level-down" data-filter="level-down"><i class="fa fa-fw fa-level-down fa-sm"></i></a>
			<a class="icon" title="check-square" data-icon="fa-check-square" data-filter="check-square"><i class="fa fa-fw fa-check-square fa-sm"></i></a>
			<a class="icon" title="pencil-square" data-icon="fa-pencil-square" data-filter="pencil-square"><i class="fa fa-fw fa-pencil-square fa-sm"></i></a>
			<a class="icon" title="external-link-square" data-icon="fa-external-link-square" data-filter="external-link-square"><i class="fa fa-fw fa-external-link-square fa-sm"></i></a>
			<a class="icon" title="share-square" data-icon="fa-share-square" data-filter="share-square"><i class="fa fa-fw fa-share-square fa-sm"></i></a>
			<a class="icon" title="compass" data-icon="fa-compass" data-filter="compass"><i class="fa fa-fw fa-compass fa-sm"></i></a>
			<a class="icon" title="caret-square-o-down" data-icon="fa-caret-square-o-down" data-filter="caret-square-o-down"><i class="fa fa-fw fa-caret-square-o-down fa-sm"></i></a>
			<a class="icon" title="caret-square-o-up" data-icon="fa-caret-square-o-up" data-filter="caret-square-o-up"><i class="fa fa-fw fa-caret-square-o-up fa-sm"></i></a>
			<a class="icon" title="caret-square-o-right" data-icon="fa-caret-square-o-right" data-filter="caret-square-o-right"><i class="fa fa-fw fa-caret-square-o-right fa-sm"></i></a>
			<a class="icon" title="eur" data-icon="fa-eur" data-filter="eur"><i class="fa fa-fw fa-eur fa-sm"></i></a>
			<a class="icon" title="gbp" data-icon="fa-gbp" data-filter="gbp"><i class="fa fa-fw fa-gbp fa-sm"></i></a>
			<a class="icon" title="usd" data-icon="fa-usd" data-filter="usd"><i class="fa fa-fw fa-usd fa-sm"></i></a>
			<a class="icon" title="inr" data-icon="fa-inr" data-filter="inr"><i class="fa fa-fw fa-inr fa-sm"></i></a>
			<a class="icon" title="jpy" data-icon="fa-jpy" data-filter="jpy"><i class="fa fa-fw fa-jpy fa-sm"></i></a>
			<a class="icon" title="rub" data-icon="fa-rub" data-filter="rub"><i class="fa fa-fw fa-rub fa-sm"></i></a>
			<a class="icon" title="krw" data-icon="fa-krw" data-filter="krw"><i class="fa fa-fw fa-krw fa-sm"></i></a>
			<a class="icon" title="btc" data-icon="fa-btc" data-filter="btc"><i class="fa fa-fw fa-btc fa-sm"></i></a>
			<a class="icon" title="file" data-icon="fa-file" data-filter="file"><i class="fa fa-fw fa-file fa-sm"></i></a>
			<a class="icon" title="file-text" data-icon="fa-file-text" data-filter="file-text"><i class="fa fa-fw fa-file-text fa-sm"></i></a>
			<a class="icon" title="sort-alpha-asc" data-icon="fa-sort-alpha-asc" data-filter="sort-alpha-asc"><i class="fa fa-fw fa-sort-alpha-asc fa-sm"></i></a>
			<a class="icon" title="sort-alpha-desc" data-icon="fa-sort-alpha-desc" data-filter="sort-alpha-desc"><i class="fa fa-fw fa-sort-alpha-desc fa-sm"></i></a>
			<a class="icon" title="sort-amount-asc" data-icon="fa-sort-amount-asc" data-filter="sort-amount-asc"><i class="fa fa-fw fa-sort-amount-asc fa-sm"></i></a>
			<a class="icon" title="sort-amount-desc" data-icon="fa-sort-amount-desc" data-filter="sort-amount-desc"><i class="fa fa-fw fa-sort-amount-desc fa-sm"></i></a>
			<a class="icon" title="sort-numeric-asc" data-icon="fa-sort-numeric-asc" data-filter="sort-numeric-asc"><i class="fa fa-fw fa-sort-numeric-asc fa-sm"></i></a>
			<a class="icon" title="sort-numeric-desc" data-icon="fa-sort-numeric-desc" data-filter="sort-numeric-desc"><i class="fa fa-fw fa-sort-numeric-desc fa-sm"></i></a>
			<a class="icon" title="thumbs-up" data-icon="fa-thumbs-up" data-filter="thumbs-up"><i class="fa fa-fw fa-thumbs-up fa-sm"></i></a>
			<a class="icon" title="thumbs-down" data-icon="fa-thumbs-down" data-filter="thumbs-down"><i class="fa fa-fw fa-thumbs-down fa-sm"></i></a>
			<a class="icon" title="youtube-square" data-icon="fa-youtube-square" data-filter="youtube-square"><i class="fa fa-fw fa-youtube-square fa-sm"></i></a>
			<a class="icon" title="youtube" data-icon="fa-youtube" data-filter="youtube"><i class="fa fa-fw fa-youtube fa-sm"></i></a>
			<a class="icon" title="xing" data-icon="fa-xing" data-filter="xing"><i class="fa fa-fw fa-xing fa-sm"></i></a>
			<a class="icon" title="xing-square" data-icon="fa-xing-square" data-filter="xing-square"><i class="fa fa-fw fa-xing-square fa-sm"></i></a>
			<a class="icon" title="youtube-play" data-icon="fa-youtube-play" data-filter="youtube-play"><i class="fa fa-fw fa-youtube-play fa-sm"></i></a>
			<a class="icon" title="dropbox" data-icon="fa-dropbox" data-filter="dropbox"><i class="fa fa-fw fa-dropbox fa-sm"></i></a>
			<a class="icon" title="stack-overflow" data-icon="fa-stack-overflow" data-filter="stack-overflow"><i class="fa fa-fw fa-stack-overflow fa-sm"></i></a>
			<a class="icon" title="instagram" data-icon="fa-instagram" data-filter="instagram"><i class="fa fa-fw fa-instagram fa-sm"></i></a>
			<a class="icon" title="adn" data-icon="fa-adn" data-filter="adn"><i class="fa fa-fw fa-adn fa-sm"></i></a>
			<a class="icon" title="bitbucket-square" data-icon="fa-bitbucket-square" data-filter="bitbucket-square"><i class="fa fa-fw fa-bitbucket-square fa-sm"></i></a>
			<a class="icon" title="tumblr" data-icon="fa-tumblr" data-filter="tumblr"><i class="fa fa-fw fa-tumblr fa-sm"></i></a>
			<a class="icon" title="tumblr-square" data-icon="fa-tumblr-square" data-filter="tumblr-square"><i class="fa fa-fw fa-tumblr-square fa-sm"></i></a>
			<a class="icon" title="long-arrow-down" data-icon="fa-long-arrow-down" data-filter="long-arrow-down"><i class="fa fa-fw fa-long-arrow-down fa-sm"></i></a>
			<a class="icon" title="long-arrow-up" data-icon="fa-long-arrow-up" data-filter="long-arrow-up"><i class="fa fa-fw fa-long-arrow-up fa-sm"></i></a>
			<a class="icon" title="long-arrow-left" data-icon="fa-long-arrow-left" data-filter="long-arrow-left"><i class="fa fa-fw fa-long-arrow-left fa-sm"></i></a>
			<a class="icon" title="long-arrow-right" data-icon="fa-long-arrow-right" data-filter="long-arrow-right"><i class="fa fa-fw fa-long-arrow-right fa-sm"></i></a>
			<a class="icon" title="apple" data-icon="fa-apple" data-filter="apple"><i class="fa fa-fw fa-apple fa-sm"></i></a>
			<a class="icon" title="windows" data-icon="fa-windows" data-filter="windows"><i class="fa fa-fw fa-windows fa-sm"></i></a>
			<a class="icon" title="android" data-icon="fa-android" data-filter="android"><i class="fa fa-fw fa-android fa-sm"></i></a>
			<a class="icon" title="linux" data-icon="fa-linux" data-filter="linux"><i class="fa fa-fw fa-linux fa-sm"></i></a>
			<a class="icon" title="dribbble" data-icon="fa-dribbble" data-filter="dribbble"><i class="fa fa-fw fa-dribbble fa-sm"></i></a>
			<a class="icon" title="skype" data-icon="fa-skype" data-filter="skype"><i class="fa fa-fw fa-skype fa-sm"></i></a>
			<a class="icon" title="foursquare" data-icon="fa-foursquare" data-filter="foursquare"><i class="fa fa-fw fa-foursquare fa-sm"></i></a>
			<a class="icon" title="trello" data-icon="fa-trello" data-filter="trello"><i class="fa fa-fw fa-trello fa-sm"></i></a>
			<a class="icon" title="female" data-icon="fa-female" data-filter="female"><i class="fa fa-fw fa-female fa-sm"></i></a>
			<a class="icon" title="male" data-icon="fa-male" data-filter="male"><i class="fa fa-fw fa-male fa-sm"></i></a>
			<a class="icon" title="gittip" data-icon="fa-gittip" data-filter="gittip"><i class="fa fa-fw fa-gittip fa-sm"></i></a>
			<a class="icon" title="sun-o" data-icon="fa-sun-o" data-filter="sun-o"><i class="fa fa-fw fa-sun-o fa-sm"></i></a>
			<a class="icon" title="moon-o" data-icon="fa-moon-o" data-filter="moon-o"><i class="fa fa-fw fa-moon-o fa-sm"></i></a>
			<a class="icon" title="archive" data-icon="fa-archive" data-filter="archive"><i class="fa fa-fw fa-archive fa-sm"></i></a>
			<a class="icon" title="bug" data-icon="fa-bug" data-filter="bug"><i class="fa fa-fw fa-bug fa-sm"></i></a>
			<a class="icon" title="vk" data-icon="fa-vk" data-filter="vk"><i class="fa fa-fw fa-vk fa-sm"></i></a>
			<a class="icon" title="weibo" data-icon="fa-weibo" data-filter="weibo"><i class="fa fa-fw fa-weibo fa-sm"></i></a>
			<a class="icon" title="renren" data-icon="fa-renren" data-filter="renren"><i class="fa fa-fw fa-renren fa-sm"></i></a>
			<a class="icon" title="pagelines" data-icon="fa-pagelines" data-filter="pagelines"><i class="fa fa-fw fa-pagelines fa-sm"></i></a>
			<a class="icon" title="stack-exchange" data-icon="fa-stack-exchange" data-filter="stack-exchange"><i class="fa fa-fw fa-stack-exchange fa-sm"></i></a>
			<a class="icon" title="arrow-circle-o-right" data-icon="fa-arrow-circle-o-right" data-filter="arrow-circle-o-right"><i class="fa fa-fw fa-arrow-circle-o-right fa-sm"></i></a>
			<a class="icon" title="arrow-circle-o-left" data-icon="fa-arrow-circle-o-left" data-filter="arrow-circle-o-left"><i class="fa fa-fw fa-arrow-circle-o-left fa-sm"></i></a>
			<a class="icon" title="caret-square-o-left" data-icon="fa-caret-square-o-left" data-filter="caret-square-o-left"><i class="fa fa-fw fa-caret-square-o-left fa-sm"></i></a>
			<a class="icon" title="dot-circle-o" data-icon="fa-dot-circle-o" data-filter="dot-circle-o"><i class="fa fa-fw fa-dot-circle-o fa-sm"></i></a>
			<a class="icon" title="wheelchair" data-icon="fa-wheelchair" data-filter="wheelchair"><i class="fa fa-fw fa-wheelchair fa-sm"></i></a>
			<a class="icon" title="vimeo-square" data-icon="fa-vimeo-square" data-filter="vimeo-square"><i class="fa fa-fw fa-vimeo-square fa-sm"></i></a>
			<a class="icon" title="try" data-icon="fa-try" data-filter="try"><i class="fa fa-fw fa-try fa-sm"></i></a>
			<a class="icon" title="plus-square-o" data-icon="fa-plus-square-o" data-filter="plus-square-o"><i class="fa fa-fw fa-plus-square-o fa-sm"></i></a>
			<a class="icon" title="plus-square-o" data-icon="fa-plus-square-o" data-filter="plus-square-o"><i class="fa fa-fw fa-plus-square-o fa-sm"></i></a>
		</div>
	</body>

</html>