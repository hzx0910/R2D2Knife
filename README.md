# R2D2Knife
Bind Android views and callbacks to fields and methods like **ButterKnife**

Eliminate findViewById calls by using @InjectSameId on fields.
Eliminate anonymous inner-classes for listeners by annotating methods with @OnClickSameId and others.

Better than butterknife：**Bind same view type together !**

    class TestActivity extends Activity {
        @InjectSameId
        private EditText email, password;
        @InjectSameId
        private View login_progress, login_form;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            R2d2Knife.injectSameId(this, R.id.class);

        }

        @OnClickSameId
        private void email_sign_in_button() {
        //todo
        }
    }
