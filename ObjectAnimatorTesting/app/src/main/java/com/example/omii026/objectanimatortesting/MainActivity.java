package com.example.omii026.objectanimatortesting;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.support.v4.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.Display;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements Frag1.changeImageListener{

    private Button btn1;
    private ImageView image1,image2,image3,image4;
    private FloatingActionButton fab;
    private boolean c;
    private ViewGroup viewGroup;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        getSupportFragmentManager().beginTransaction().add(R.id.contain,new Frag1()).addToBackStack(null).commit();
        fab = (FloatingActionButton) findViewById(R.id.fab);
        btn1 = (Button) findViewById(R.id.btn1);
        image1 = (ImageView) findViewById(R.id.image1);
        image2 = (ImageView) findViewById(R.id.image2);
        image3 = (ImageView) findViewById(R.id.image3);
        image4 = (ImageView) findViewById(R.id.image4);

        final Button btn2 = (Button) findViewById(R.id.btn2);
        textView = (TextView) findViewById(R.id.textView);

//        example1();       //example #1     change content_main.xml to content_main in activity_main
//        example2();       //example #2     change content_main.xml to content_main in activity_main
//        example3();       //example #3     change content_main.xml to content_main in activity_main
//        example4();       //example #4     change content_main.xml to content_main2 in activity_main
//        example5();       //example #5     change content_main.xml to content_main2 in activity_main
//        example6();       //example #6     change content_main.xml to content_main2 in activity_main
//        example7();       //example #7     change content_main.xml to content_main3 in activity_main
//        example8();       //example #8     change content_main.xml to content_main3 in activity_main
//        example9();       //example #9     change content_main.xml to content_main4 in activity_main
//        example10();      //example #10    change content_main.xml to content_main5 in activity_main
          example11();      //example #11    change content_main.xml to content_main2 in activity_main

    }



    private void example1() {
     //example #1

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ObjectAnimator.ofFloat(btn1,"alpha",0f).start();
//                btn1.animate().alpha(0);
                if(c == false) {
                    btn1.animate().alpha(1f).x(500).y(800).setDuration(1000);
                    c= true;
                }

                else if(c == true){
                    btn1.animate().alpha(1f).x(50).y(50).setDuration(1000);
                    c=false;
                }
            }
        });


    }
    private void example2() {

        //example 2#

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ObjectAnimator animX = ObjectAnimator.ofFloat(btn1,"x",50f);
                ObjectAnimator animY = ObjectAnimator.ofFloat(btn1,"y",100f);

                AnimatorSet animSetXY = new AnimatorSet();
                animSetXY.playTogether(animX,animY);
                animSetXY.start();
            }
        });


    }
    private void example3() {


        //example #3

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(c == false) {
                    btn1.animate().alpha(1f).x(500).y(800).rotationYBy(720).setDuration(1000);
                    c= true;
                }

                else if(c == true){
                    btn1.animate().alpha(1f).x(50).y(50).rotationYBy(720).setDuration(1000);
                    c=false;
                }
            }
        });



    }
    private void example4() {

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(c == false) {
//                    image1.animate().translationX(400).setDuration(1000).withLayer();
                    image1.animate().translationX(400).withLayer();

                    c= true;
                }

                else if(c == true){
//                    image1.animate().translationX(50).setDuration(1000).withLayer();
                    image1.animate().translationX(50).withLayer();
                    c=false;
                }

            }
        });


    }
    private void example5() {


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (c == false) {
                    image1.animate().translationX(400).withStartAction(new Runnable() {
                        @Override
                        public void run() {
                            image2.setTranslationX(image1.getWidth());
                        }
                    });

                    c = true;
                } else if (c == true) {
                    image1.animate().alpha(1f).x(50).y(50).setDuration(1000);
                    image2.animate().alpha(1f).x(50).y(500).setDuration(1000);
                    c = false;
                }
            }
        });


    }
    private void example6() {


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (c == false) {
                    image1.animate().translationX(400).withStartAction(new Runnable() {
                        @Override
                        public void run() {
                            image2.setTranslationX(image1.getWidth());
                        }
                    });

                    c = true;
                } else if (c == true) {
                    image1.animate().alpha(1f).x(50).y(50).setDuration(1000);
                    image2.animate().alpha(1f).x(50).y(500).setDuration(1000);
                    c = false;
                }

            }
        });


    }
    private void example7() {

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutTransition layoutTransition = new LayoutTransition();
                layoutTransition.enableTransitionType(LayoutTransition.CHANGING);
                viewGroup = (ViewGroup) findViewById(R.id.container);
                viewGroup.setLayoutTransition(layoutTransition);
                viewGroup.addView(new Button(MainActivity.this));
            }
        });


    }
    private void example8() {

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(textView,"rotationY",0.0f,360f);
                animator.setDuration(3600);
                animator.setRepeatCount(ObjectAnimator.INFINITE);
                animator.start();

            }
        });


    }
    private void example9() {


        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                image1.animate().x(200).y(80).setDuration(500).start();
                Display display = getWindowManager().getDefaultDisplay();
                float wigth = display.getWidth();
                float height = display.getHeight();

                ObjectAnimator animX = ObjectAnimator.ofFloat(image1,"x",300);
                animX.setDuration(500);

                ObjectAnimator animY = ObjectAnimator.ofFloat(image1,"y",100);
                animX.setDuration(500);

                ObjectAnimator animY2 = ObjectAnimator.ofFloat(image1,"y",200);
                animX.setDuration(500);
//
                ObjectAnimator moveToDefaultXOnStart = ObjectAnimator.ofFloat(image1, "scaleX", 2.5f);
                moveToDefaultXOnStart.setDuration(500);

                ObjectAnimator moveToDefaultYOnStart = ObjectAnimator.ofFloat(image1, "scaleY", 2f);
                moveToDefaultYOnStart.setDuration(500);


                ObjectAnimator fadeInAnimator = ObjectAnimator.ofFloat(image1, "alpha", 0, 1);
                fadeInAnimator.setDuration(1000);
                fadeInAnimator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                        image1.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {

                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });

                AnimatorSet animatorSet = new AnimatorSet();

                animatorSet.play(animY).before(moveToDefaultXOnStart);
                animatorSet.play(moveToDefaultXOnStart).with(moveToDefaultYOnStart).with(animY2);

//                animatorSet.play(animY).before(moveToDefaultYOnStart);
//                animatorSet.play(animY2).after(moveToDefaultYOnStart);
//                animatorSet.play(moveToDefaultXOnStart);
//                animatorSet.play(moveToDefaultYOnStart);
                animatorSet.start();
            }
        });




    }
    private void example10() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
    }
    private void example11() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(c == false) {
//                    image1.animate().translationX(400).setDuration(1000).withLayer();
                    image1.animate().translationX(-150).setDuration(400).withLayer();
                    image2.animate().translationX(-150).setDuration(300).withLayer();
                    image3.animate().translationX(-150).setDuration(200).withLayer();
                    image4.animate().translationX(-150).setDuration(100).withLayer();

                    c= true;
                }

                else if(c == true){
//                    image1.animate().translationX(50).setDuration(1000).withLayer();
                    image1.animate().translationX(0).setDuration(100).withLayer();
                    image2.animate().translationX(0).setDuration(200).withLayer();
                    image3.animate().translationX(0).setDuration(300).withLayer();
                    image4.animate().translationX(0).setDuration(400).withLayer();
                    c=false;
                }

            }
        });
    }

    private void startAnimation() {

        ObjectAnimator moveToDefaultXOnStart = ObjectAnimator.ofFloat(image1, "scaleX", 1.6f);
        moveToDefaultXOnStart.setDuration(10);

        ObjectAnimator moveToDefaultYOnStart = ObjectAnimator.ofFloat(image1, "scaleY", 1.6f);
        moveToDefaultYOnStart.setDuration(10);


        ObjectAnimator fadeInAnimator = ObjectAnimator.ofFloat(image1, "alpha", 0, 1);
        fadeInAnimator.setDuration(1000);
        fadeInAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                image1.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(fadeInAnimator);
        animatorSet.play(moveToDefaultXOnStart);
        animatorSet.play(moveToDefaultYOnStart);
        animatorSet.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void change() {

        Frag1 startFrom = Frag1.frag1;
//        int contain = R.id.contain;
        View imageView = Frag1.viewOfImage;
        Frag2 frag2 = new Frag2();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Transition transition1 = TransitionInflater.from(MainActivity.this)
                    .inflateTransition(R.transition.change_image_transform);

            startFrom.setSharedElementReturnTransition(transition1);
            startFrom.setAllowEnterTransitionOverlap(false);
            startFrom.setAllowReturnTransitionOverlap(false);

            frag2.setSharedElementReturnTransition(transition1);
            frag2.setAllowEnterTransitionOverlap(false);
            frag2.setAllowReturnTransitionOverlap(false);
            frag2.setImageId(imageView.getTransitionName());

//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(contain,frag2)
//                    .addToBackStack(null)
//                    .addSharedElement(imageView, imageView.getTransitionName())
//                    .commit();


        }


    }
}
