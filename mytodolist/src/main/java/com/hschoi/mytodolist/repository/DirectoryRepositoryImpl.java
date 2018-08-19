package com.hschoi.mytodolist.repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;

import com.hschoi.mytodolist.entity.DirectoryEntity;

/**
* <pre>
* com.hschoi.myTodoList.repository
* </pre>
*
* Desc 	   : DirectoryRepository 구현 클래스
* @Company : eBay Japan. Inc
* @Author  : hychoi
* @Date    : 2018. 8. 18.
* @Version :
*/
public class DirectoryRepositoryImpl extends QueryDslRepositorySupport implements MyDirectoryRepository {
	
	public DirectoryRepositoryImpl () {
        super(DirectoryEntity.class);
    }

	public void createDirectoryById (int parents, int children) {
        EntityManager em = getEntityManager();

        StringBuffer sb = new StringBuffer();

        sb.append("INSERT INTO DIRECTORY (PARENTS, CHILDREN) SELECT DI.PARENTS, ");
        sb.append(children);
        sb.append("  FROM DIRECTORY DI WHERE DI.CHILDREN = ?1 UNION ALL SELECT ");
        sb.append(children);
        sb.append(",");
        sb.append(children);

        Query qry  = em.createNativeQuery(sb.toString());

        qry.setParameter(1, parents);

        qry.executeUpdate();

        em.close();
    }

	@Override
	public void disconnectDir(int rootId) {
		// TODO Auto-generated method stub
		
		StringBuffer sb = new StringBuffer();

		sb.append("	DELETE	\n											");
		sb.append("   FROM 	DIRECTORY\n									");
		sb.append("  WHERE 	CHILDREN IN  (\n							");
		sb.append("                      	SELECT CHILDREN\n			");
		sb.append("                           FROM DIRECTORY\n			");
		sb.append("                       	 WHERE PARENTS = ?1\n		");
		sb.append("                      )\n							");
		sb.append("    AND PARENTS 	 IN  (\n							");
		sb.append("                     	SELECT PARENTS\n			");
		sb.append("                       	  FROM DIRECTORY\n			");
		sb.append("                      	 WHERE CHILDREN = ?2\n		");
		sb.append("                        	   AND PARENTS != CHILDREN\n");
		sb.append("                   )									");

		EntityManager em = getEntityManager();

		Query qry = em.createNativeQuery(sb.toString());

		qry.setParameter(1, rootId);
		qry.setParameter(2, rootId);

		qry.executeUpdate();

		em.close();		
		
	}

	@Override
	public void attechNewRoot(int ordRootId, int NewRootId) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();

		sb.append("	INSERT INTO \n                              ");
		sb.append("		DIRECTORY (PARENTS, CHILDREN) \n        ");
		sb.append("		( \n                                    ");
		sb.append("			SELECT DIR.PARENTS, SDIR.CHILDREN \n");
		sb.append("			  FROM DIRECTORY DIR \n             ");
		sb.append("			 CROSS JOIN DIRECTORY SDIR \n       ");
		sb.append("			 WHERE DIR.CHILDREN = ? \n          ");
		sb.append("			   AND SDIR.PARENTS = ? \n          ");
		sb.append("		) \n                                    ");

		EntityManager em = getEntityManager();

		Query qry = em.createNativeQuery(sb.toString());

		qry.setParameter(1, NewRootId);
		qry.setParameter(2, ordRootId);

		qry.executeUpdate();

		em.close();	
	}

}
