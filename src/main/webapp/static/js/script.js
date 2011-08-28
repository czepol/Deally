jQuery(function($){
  $('.s_switcher').hover(function() {
    $(this).find('.s_options').stop(true, true).slideDown('fast');
  },function() {
    $(this).find('.s_options').stop(true, true).slideUp('fast');
  });
});
