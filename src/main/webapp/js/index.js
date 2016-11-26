 $(document).ready(function(){
             $('.sidebar ul li').click(function(){
             var index=$('.sidebar ul li').index(this);
               var num = $(this).index();
             if (num%3==0){$(this).addClass('cur');$(this).siblings().removeClass('cur1').removeClass('cur2');
                $(this).children().css("color","#fff");
                 $('.sidebar ul li').eq(1).addClass("bm1");
                $('.sidebar ul li').eq(1).children().css("color","#95a0aa");
                 $('.sidebar ul li').eq(2).children().css("color","#95a0aa");
           }
             if (num%3==1){$(this).addClass('cur1');$(this).siblings().removeClass('cur2').removeClass('cur');
                $(this).children().css("color","#fff");
                $('.sidebar ul li').eq(0).addClass("bm");
                $('.sidebar ul li').eq(0).children().css("color","#95a0aa");
                 $('.sidebar ul li').eq(2).children().css("color","#95a0aa");

             }
            if (num%3==2){$(this).addClass('cur2');$(this).siblings().removeClass('cur').removeClass('cur1');
               $(this).children().css("color","#fff");
            
                $('.sidebar ul li').eq(1).children().css("color","#95a0aa");
                 $('.sidebar ul li').eq(0).children().css("color","#95a0aa");
               

          }
            
           $('.maintain').eq(index) .addClass('on').siblings('.maintain').removeClass('on');    
      });


       })