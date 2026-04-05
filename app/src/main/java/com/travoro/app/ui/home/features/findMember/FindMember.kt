package com.travoro.app.ui.home.features.findMember

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.travoro.app.ui.components.CustomTopBar
import kotlinx.coroutines.delay
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import kotlin.random.Random

@Composable
fun FindMemberScreen(
    homeNavController: NavController
) {

    var mapView by remember { mutableStateOf<MapView?>(null) }

    /* trigger recomposition */
    var tick by remember { mutableStateOf(0) }

    /* 5 fake members */
    val members = remember {

        mutableStateListOf(

            GeoPoint(28.6139,77.2090),
            GeoPoint(28.6145,77.2100),
            GeoPoint(28.6125,77.2080),
            GeoPoint(28.6152,77.2070),
            GeoPoint(28.6118,77.2115)

        )

    }


    /* random movement simulation */
    LaunchedEffect(Unit){

        while(true){

            delay(3000)

            for(i in members.indices){

                val p = members[i]

                members[i] = GeoPoint(

                    p.latitude +
                            Random.nextDouble(-0.001,0.001),

                    p.longitude +
                            Random.nextDouble(-0.001,0.001)

                )

            }

            tick++ // force update

        }

    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ){

        CustomTopBar(
            title = "FIND CREW MEMBER",
            onBackClick = { homeNavController.popBackStack() }
        )


        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ){

            Box(
                modifier = Modifier
                    .fillMaxWidth(0.92f)
                    .fillMaxHeight(0.95f)
                    .padding(top = 16.dp)
                    .background(
                        MaterialTheme.colorScheme.surface,
                        RoundedCornerShape(20.dp)
                    )
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                        RoundedCornerShape(20.dp)
                    )
            ){

                AndroidView(

                    factory = { context ->

                        Configuration.getInstance()
                            .userAgentValue = context.packageName

                        MapView(context).apply {

                            setTileSource(
                                TileSourceFactory.MAPNIK
                            )

                            controller.setZoom(16.0)

                            controller.setCenter(
                                members.first()
                            )

                            mapView = this

                        }

                    },

                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(20.dp))

                )

            }

        }

    }



    /* update markers */
    LaunchedEffect(tick){

        mapView?.let { map ->

            map.overlays.clear()

            members.forEachIndexed { index, point ->

                val marker = Marker(map)

                marker.position = point

                marker.title = "Member ${index+1}"

                marker.setAnchor(
                    Marker.ANCHOR_CENTER,
                    Marker.ANCHOR_BOTTOM
                )

                map.overlays.add(marker)

            }

            map.invalidate()

        }

    }

}


