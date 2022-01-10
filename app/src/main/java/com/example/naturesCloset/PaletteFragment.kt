package com.example.naturesCloset

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.naturesCloset.databinding.FragmentPaletteBinding
import android.content.ClipDescription

import android.content.ClipData
import android.content.Intent
import android.graphics.*

import android.graphics.drawable.ColorDrawable

import android.view.DragEvent
import android.graphics.drawable.Drawable
import android.view.View.*
import android.widget.ImageView
import androidx.palette.graphics.Palette
import android.graphics.drawable.BitmapDrawable

import android.graphics.Bitmap
import android.net.Uri
import com.example.naturesCloset.classDirectory.Colors
import com.example.naturesCloset.classDirectory.LoginResponse
import com.example.naturesCloset.serviceDirectory.SaveMyPaletteService
import com.example.naturesCloset.serviceDirectory.ShareService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class PaletteFragment : Fragment(){

    public val pColors = Colors()
    public var upcolor = ""
    public var downcolor = ""

    private lateinit var binding: FragmentPaletteBinding
    private val photoList: ArrayList<Uri> = ArrayList()
    private var mDragListener: MyDragEventListener? = null
    lateinit var colorname: String
    var share: LoginResponse? = null


    companion object{
        const val TAG : String = "로그"
        fun newInstance(): PaletteFragment{
            return PaletteFragment()
        }
    }

    // 메모리에 올라갔을 때
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "HomeFragment - onCreate called")

    }


    // 프레그먼트를 안고 있는 액티비티에 붙었을 때
    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "HomeFragment - onAttach() called")
    }


    //뷰 생성
    // fragment와 레이아웃 연결
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(ProfileFragment.TAG, "HomeFragment - onCreateView() called")
        binding = FragmentPaletteBinding.inflate(inflater, container, false)

        if(arguments != null){
            val uris: ArrayList<String> = arguments?.getStringArrayList("img") as ArrayList<String>
            for(i in 0 until (uris.size)){
                photoList.add(Uri.parse(uris[i]))
            }

            val aPhoto = photoList[0] as Uri
            binding.sampleImg.setImageURI(aPhoto)
        }

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.249.18.165") // 주소는 본인의 서버 주소로 설정
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var saveMyPaletteService: SaveMyPaletteService = retrofit.create(SaveMyPaletteService::class.java)



        val bitmap = (binding.sampleImg.getDrawable() as BitmapDrawable).bitmap

        /* ============ 버튼 눌러서 통신하기 =========*/

        binding.saveBtn.setOnClickListener {

            val intent = Intent(MyApplication.ApplicationContext(), MainActivity::class.java)
            colorname = binding.colorTitle.getText().toString()

            saveMyPaletteService.requestLogin(
                "testname",
                colorname,
                pColors.col1,
                pColors.col2,
                pColors.col3,
                pColors.col4,
                pColors.col5,
                pColors.col6,
                mDragListener!!.upcolor,
                mDragListener!!.downcolor,
            ).enqueue(object :
                Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.e("POST", "============Post Error!==========")
                }

                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    share = response.body()
                    Log.d("LOGIN", "============Login Success!!==========")
                    startActivity(intent)
                }
            })
        }


            setPaletteColor(bitmap)

        mDragListener = MyDragEventListener()


        binding.shirt.setOnDragListener(mDragListener) // 셔츠에 draglistener를 넣어둔다.
        binding.pants.setOnDragListener(mDragListener) // 셔츠에 draglistener를 넣어둔다.


        binding.color1.setOnLongClickListener(MyLongClickListener()) //color1에 LongClickListener 를 넣어준다.
        binding.color2.setOnLongClickListener(MyLongClickListener())
        binding.color3.setOnLongClickListener(MyLongClickListener())
        binding.color4.setOnLongClickListener(MyLongClickListener())
        binding.color5.setOnLongClickListener(MyLongClickListener())
        binding.color6.setOnLongClickListener(MyLongClickListener())

    }

    /* 여기부터 드래그&드롭, 색 바뀌는 코드 */
    private class MyLongClickListener : OnLongClickListener {

        override fun onLongClick(view: View): Boolean {

            var draw: ColorDrawable = view.background as ColorDrawable
            var color_id = draw.getColor()

            val colorString = Integer.toHexString(color_id)

            val item = ClipData.Item(colorString)

            val dragData = ClipData(
                colorString, arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN), item
            )

            // Instantiates the drag shadow builder
            val myShadow: DragShadowBuilder = MyDragShadowBuilder(view)

            // Starts the drag
            view.startDrag(
                dragData,  //  the data to be drag
                myShadow,  // the drag shadow builder
                null,  // no need to use local data
                0 // flags
            )
            return false
        }
    }

    private class MyDragShadowBuilder(v: View) : DragShadowBuilder(v) {


        // Defines a callback that sends the drag shadow dimensions and touch point back to the system
        override fun onProvideShadowMetrics(size: Point, touch: Point) {
            // Define local variables
            val width: Int
            val height: Int

            // Sets the width of the shadow to half the width of the original view
            width = view.width / 2

            // Sets the height of the shadow to half the height of the original view
            height = view.height / 2

            // The drag shadow will fill the Canvas
            shadow.setBounds(0, 0, width, height)

            // Sets the size parameter's width and height values
            size.set(width, height)

        // Sets the touch point position to be in the middle of the drag shadow
        touch.set(width / 2, height / 2)
    }

    override fun onDrawShadow(canvas: Canvas) {
            shadow.draw(canvas)
        }

        companion object {
            private var shadow: Drawable = ColorDrawable()
        }

        init {

            val color = (v.background as ColorDrawable).color
            shadow = ColorDrawable(getDarkerColor(color))
        }

        fun getDarkerColor(color: Int): Int {
            val hsv = FloatArray(3)
            Color.colorToHSV(color, hsv)
            //hsv[2] = 0.8f *hsv[2];
            hsv[2] = 0.7f * hsv[2] // more darker
            return Color.HSVToColor(hsv)
        }
    }

    class MyDragEventListener : OnDragListener {

        public var upcolor = "#000000"
        public var downcolor = "#000000"

        override fun onDrag(view: View, event: DragEvent): Boolean {
            // Define the variable to store the action type for the incoming event
            val action = event.action
            when (action) {
                DragEvent.ACTION_DRAG_STARTED -> {
                    // Determine if this view can accept dragged data
                    if (event.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                        // If the view view can accept dragged data
                        // Return true to indicate that the view can accept the dragged data
                        return true
                    }
                    return false
                }
                DragEvent.ACTION_DRAG_ENTERED -> {
                    // When dragged item entered the receiver view area
                    return true
                }
                DragEvent.ACTION_DRAG_LOCATION ->                     // Ignore the event
                    return true
                DragEvent.ACTION_DRAG_EXITED -> {
                    // When dragged object exit the receiver object
                    // Return true to indicate the dragged object exited the receiver view
                    return true
                }
                DragEvent.ACTION_DROP -> {
                    // Get the dragged data
                    val item = event.clipData.getItemAt(0) //클립보드에 복붙하는 방식인듯.
                    val dragData = item.text as String //Dragdata가 보이지 않는 문제!

                    // Cast the receiver view as a TextView object
                    val v = view as ImageView

                    val v_id = view.getResources().getResourceName(v.getId()).toString()
                    val clothes = v_id.split("/")[1]


                    // Change the TextView text color as dragged object background color
                    Log.d("Here is Drop Data", dragData)
                    Log.d("Here is id of dropped view", clothes)

                    if(clothes == "shirt"){
                        upcolor = "#"+dragData.substring(2)
                    }
                    else if(clothes == "pants"){
                        downcolor = "#"+dragData.substring(2)
                    }

                    v.setColorFilter(Color.parseColor("#"+dragData), PorterDuff.Mode.MULTIPLY)
                    // Return true to indicate the dragged object dop
                    return true
                }
                DragEvent.ACTION_DRAG_ENDED -> {
                    // Remove the background color from view
                    view.setBackgroundColor(Color.TRANSPARENT)
                    // Return true to indicate the drag ended
                    return true
                }
                else -> Log.e("Drag and Drop example", "Unknown action type received.")
            }
            return false
        }
    }


    /*palatte API */
    fun createPaletteSync(bitmap: Bitmap): Palette = Palette.from(bitmap).generate()

    // Set the background and text colors of a toolbar given a
    // bitmap image to match

    fun setPaletteColor(bitmap: Bitmap) {
        // Generate the palette and get the vibrant swatch
        val palette = createPaletteSync(bitmap)

        val color: Int = Color.rgb(255, 255,255) //null 일 때 기본 color setting

        if(palette==null) return;

        pColors.col1 = "#" + Integer.toHexString(palette.vibrantSwatch?.rgb?: color).substring(2)
        pColors.col2 = "#" + Integer.toHexString(palette.darkVibrantSwatch?.rgb ?: color).substring(2)
        pColors.col3 = "#" + Integer.toHexString(palette.lightVibrantSwatch?.rgb ?: color).substring(2)
        pColors.col4 = "#" + Integer.toHexString(palette.mutedSwatch?.rgb ?: color).substring(2)
        pColors.col5 = "#" + Integer.toHexString(palette.darkMutedSwatch?.rgb ?: color).substring(2)
        pColors.col6 = "#" + Integer.toHexString(palette.lightMutedSwatch?.rgb ?: color).substring(2)

        binding.color1.setBackgroundColor(Color.parseColor(pColors.col1))
        binding.color2.setBackgroundColor(Color.parseColor(pColors.col2))
        binding.color3.setBackgroundColor(Color.parseColor(pColors.col3))
        binding.color4.setBackgroundColor(Color.parseColor(pColors.col4))
        binding.color5.setBackgroundColor(Color.parseColor(pColors.col5))
        binding.color6.setBackgroundColor(Color.parseColor(pColors.col6))

    }


}