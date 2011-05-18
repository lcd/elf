(function($) {
    $.fn.tipsy = function(opts) {

        opts = $.extend({fade: false, gravity: 'n'}, opts || {});
        var tip = null, cancelHide = false;

        this.hover(function() {
            
            $.data(this, 'cancel.tipsy', true);

            var tip = $.data(this, 'active.tipsy');
            if (!tip) {
                tip = $('<div class="tipsy"><div class="tipsy-inner">' + $(this).attr('title') + '</div></div>');
                tip.css({position: 'absolute', zIndex: 100000});
                $(this).attr('title', '');
                $.data(this, 'active.tipsy', tip);
            }
            
            var pos = $.extend({}, $(this).offset(), {width: this.offsetWidth, height: this.offsetHeight});
            tip.remove().css({top: 0, left: 0, visibility: 'hidden', display: 'block'}).appendTo(document.body);
            var actualWidth = tip[0].offsetWidth, actualHeight = tip[0].offsetHeight;
            
            switch (opts.gravity.charAt(0)) {
                case 'n':
                    tip.css({top: pos.top + pos.height, left: pos.left + pos.width / 2 - actualWidth / 2}).addClass('tipsy-north');
                    break;
                case 's':
                    tip.css({top: pos.top - actualHeight, left: pos.left + pos.width / 2 - actualWidth / 2}).addClass('tipsy-south');
                    break;
                case 'e':
                    tip.css({top: pos.top + pos.height / 2 - actualHeight / 2, left: pos.left - actualWidth}).addClass('tipsy-east');
                    break;
                case 'w':
                    tip.css({top: pos.top + pos.height / 2 - actualHeight / 2, left: pos.left + pos.width}).addClass('tipsy-west');
                    break;
            }

            if (opts.fade) {
                tip.css({opacity: 0, display: 'block', visibility: 'visible'}).animate({opacity: 0.8},200);
            } else {
                tip.css({visibility: 'visible'});
            }

        }, function() {
            $.data(this, 'cancel.tipsy', false);
            var self = this;
            setTimeout(function() {
                if ($.data(this, 'cancel.tipsy')) return;
                var tip = $.data(self, 'active.tipsy');
                if (opts.fade) {
                    tip.stop().fadeOut(200,function() { $(this).remove(); });
                } else {
                    tip.remove();
                }
            }, 100);
            
        });

    };
})(jQuery);

$(function(){
	$("<style>.tipsy { padding: 5px; opacity: 0.8; filter: alpha(opacity=80); background-repeat: no-repeat;}.tipsy-inner { padding: 0px 5px; background-color: black; color: white; max-width: 200px; text-align: center; -moz-border-radius:3px; -webkit-border-radius:3px; border-radius:3px;}.tipsy-north { background-image: url(../images/tipsy-north.gif); background-position: top center; }.tipsy-south { background-image: url(../images/tipsy-south.gif); background-position: bottom center; }.tipsy-east { background-image: url(../images/tipsy-east.gif); background-position: right center; }.tipsy-west { background-image: url(../images/tipsy-west.gif); background-position: left center; }</style>").appendTo("head");
	$(".tooltiped").each(function(){
		if($(this).hasClass("tipUp")){
			$(this).tipsy({fade: true,gravity: 's'});
		}else if($(this).hasClass("tipDown")){
			$(this).tipsy({fade: true,gravity: 'n'});
		}else if($(this).hasClass("tipLeft")){
			$(this).tipsy({fade: true,gravity: 'e'});
		}else if($(this).hasClass("tipRight")){
			$(this).tipsy({fade: true,gravity: 'w'});
		}else{
			$(this).tipsy({fade: true,gravity: 's'});
		}
	});
});
