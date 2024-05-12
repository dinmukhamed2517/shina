package kz.sdk.shina.fragments

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kz.sdk.shina.R
import kz.sdk.shina.adapters.ProductAdapter
import kz.sdk.shina.base.BaseFragment
import kz.sdk.shina.databinding.FragmentHomeBinding
import kz.sdk.shina.models.Product


@AndroidEntryPoint

class HomeFragment: BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    override fun onBindView() {
        super.onBindView()

        var adapter1 = ProductAdapter()
//        binding.searchBtn.setOnClickListener {
//            findNavController().navigate(
//                R.id.action_home_to_searchFragment
//            )
//        }
        binding.recycler1.adapter = adapter1
        binding.recycler1.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        adapter1.submitList(getList())

//        adapter1.itemClick = {
//            findNavController().navigate(
//                HomeFragmentDirections.actionHomeToProductDetailsFragment(
//                    it
//                )
//            )
//        }

    }

    fun getList(): List<Product> {
        return listOf(
            Product(
                1,
                "Audi e-tron GT 2021 г.",
                R.drawable.product_1,
                540000000.0,
                description = "RS e Tron GT\n" +
                        "\n" +
                        "— Самая полная комплектация в Казахстане\n" +
                        "— Полный привод\n" +
                        "— Невероятно мягкая пневмоподвеска\n" +
                        "— Массаж, подогрев и обдув сидений\n" +
                        "— Салон с эксклюзивной красной строчкой\n" +
                        "— Обилие карбона по кузову\n" +
                        "— Защитная пленка в круг\n" +
                        "— Камеры 360, максимальное количество датчиков безопасности\n" +
                        "— Карбон-керамические тормоза\n" +
                        "— 650 лошадиных сил\n" +
                        "— 400 км запас хода\n" +
                        "— Быстрая зарядка за 0.5 часа\n" +
                        "— Ночное видение\n" +
                        "— Круиз контроль и удержание полосы\n" +
                        "— Кожа, Алькантара и карбон в салоне\n" +
                        "— Комплект резины зима-лето"
            ),
            Product(
                2,
                "Jeep Grand Cherokee 2013 г",
                R.drawable.product_2,
                23500000.0,
                description = "Автомобиль в отличном состоянии, полностью обслужен (Микаэль SRT) и готов к эксплуатации. Заводской facelift. Много приятных и нужных доработок, такие как:\n" +
                        "1. Bi-LED\n" +
                        "2. Доп охлаждение масла\n" +
                        "3. Полный выхлоп Borla S\n" +
                        "4. Eibach Pro-Kit\n" +
                        "5. Фирменные диски на резине Michelin\n" +
                        "За автомобиль не стыдно, готов к показу в Алматы на неделю, позже отправится автовозом в Актобе. Приятный налог. Торг уместен."
            )
        )
    }

}