package com.websarva.wings.android.mediasample;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.io.IOException;

/**
 * 『Androidアプリ開発の教科書』
 * 第12章
 * メディアサンプル
 *
 * アクティビティクラス。
 *
 * @author Shinzo SAITO
 */
public class MediaControlActivity extends AppCompatActivity {
	/**
	 * メディアプレーヤーフィールド。
	 */
	private MediaPlayer _player;

	/**
	 * 再生・一時停止ボタンフィールド。
	 */
	private Button _btPlay;



	private Button _btPlay2;

    EditText input;

    TextView output;

    int num;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_media_control);

		//フィールドの各ボタンを取得。
		_btPlay = findViewById(R.id.btPlay);
		_btPlay2 = findViewById(R.id.btPlay2);
        input = findViewById(R.id.etInterval);
        output =findViewById(R.id.tvOutput);

        Button btClick =findViewById(R.id.btClick);
        HelloListener listener = new HelloListener();
        btClick.setOnClickListener(listener);

        if (null == input) {
            System.out.println("nullです。");
            String inputStr = "1";
        } else {
            String inputStr = input.getText().toString();
            output.setText(inputStr);


        }





		//フィールドのメディアプレーヤーオブジェクトを生成。
		_player = new MediaPlayer();
		//音声ファイルのURI文字列を作成。
		String mediaFileUriStr = "android.resource://" + getPackageName() + "/" + R.raw.mountain_stream;
		//音声ファイルのURI文字列を元にURIオブジェクトを生成。
		Uri mediaFileUri = Uri.parse(mediaFileUriStr);
		try {
			//メディアプレーヤーに音声ファイルを指定。
			_player.setDataSource(MediaControlActivity.this, mediaFileUri);
			//非同期でのメディア再生準備が完了した際のリスナを設定。
			_player.setOnPreparedListener(new PlayerPreparedListener());
			//メディア再生が終了した際のリスナを設定。
			_player.setOnCompletionListener(new PlayerCompletionListener());
			//非同期でメディア再生を準備。
			_player.prepareAsync();
		}
		catch(IOException e) {
			e.printStackTrace();
		}

	}

	private class HelloListener implements View.OnClickListener{
        @Override
        public void onClick(View view){
            if (null == input) {
                System.out.println("nullです。");
                String inputStr = "1";
            } else {

                String inputStr = input.getText().toString();
                output.setText("繰り返し間隔：" + inputStr +"秒");
            }
        }
    }

	@Override
	protected void onDestroy() {
		//親クラスのメソッド呼び出し。
		super.onDestroy();
		//プレーヤーが再生中なら…
		if(_player.isPlaying()) {
			//プレーヤーを停止。
			_player.stop();
		}
		//プレーヤーを解放。
		_player.release();
		//プレーヤー用フィールドをnullに。
		_player = null;
	}

	/**
	 * 再生ボタンタップ時の処理メソッド。
	 *
	 * @param view 画面部品
	 */
	public void onPlayButtonClick(View view) {
        String inputStr = input.getText().toString();

        int num = Integer.parseInt(inputStr);


		//プレーヤーが再生中だったら…
		if(_player.isPlaying()) {
			//プレーヤーを一時停止。
			_player.pause();
		}
		//プレーヤーが再生中じゃなかったら…
		else {
            for (int i =0;i<1440;i++) {
                //プレーヤーを再生。
                _player.start();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                _player.pause();
                try {
                    Thread.sleep(1000*num);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


		}

	}

    public void onPlayButtonClick2(View view) {
        //プレーヤーが再生中だったら…
        if(_player.isPlaying()) {
            //プレーヤーを一時停止。
            _player.pause();
            //再生ボタンのラベルを「再生」に設定。
            _btPlay2.setText(R.string.bt_play_play);
        }
        //プレーヤーが再生中じゃなかったら…
        else {
            //プレーヤーを再生。
            _player.start();
            try {
                Thread.sleep(1000);
                _player.pause();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }

    }



	/**
	 * プレーヤーの再生準備が整った時のリスナクラス。
	 */
	private class PlayerPreparedListener implements MediaPlayer.OnPreparedListener {

		@Override
		public void onPrepared(MediaPlayer mp) {
			//各ボタンをタップ可能に設定。
			_btPlay.setEnabled(true);
            _btPlay2.setEnabled(true);

        }
	}

	/**
	 * 再生が終了したときのリスナクラス。
	 */
	private class PlayerCompletionListener implements MediaPlayer.OnCompletionListener {

		@Override
		public void onCompletion(MediaPlayer mp) {
			//ループ設定がされていないならば…
			if(!_player.isLooping()) {
				//再生ボタンのラベルを「再生」に設定。
				_btPlay.setText(R.string.bt_play_play);
			}
		}
	}


}
