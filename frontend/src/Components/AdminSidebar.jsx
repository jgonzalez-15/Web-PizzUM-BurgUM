import SidebarItem from "./SidebarItem"

function AdminSidebar(){
    return(
        <div className="h-[calc(100vh-4rem)] w-64 justify-between bg-gray-100 flex flex-col">
            <div className="flex flex-col">
            <SidebarItem text='Administración' route='/admin'/>
            </div>
            <div className="w-full border-t">
                <div className="w-full m-4">
                    Administrador
                </div>
            </div>
        </div>
    )
}

export default AdminSidebar